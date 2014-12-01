/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter2.sec2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import javax.swing.JPanel;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author NgoVietLinh
 */
public class GridBagPane extends JPanel{
    private GridBagConstraints constrains;
    public GridBagPane(String fileName) {
        setLayout(new GridBagLayout());
        constrains= new GridBagConstraints();
        try {
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            if (fileName.contains("-schema")) {
                factory.setNamespaceAware(true);
                final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
                final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
                factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);                
            }
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document doc= (Document) builder.parse(new File(fileName));
            if (fileName.contains("-schema")){
                int count= removeElementContentWhiteSpace(doc.getDocumentElement());
                System.out.println(count+" whitespace nodes removed");
            }
            parseGridBag(doc.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int removeElementContentWhiteSpace(Element e) {
        NodeList children= e.getChildNodes();
        int count=0;
        boolean allTextchildrenAreWhitespace=true;
        int elements= 0;
        for (int i = 0; i < children.getLength()&& allTextchildrenAreWhitespace; i++) {
            Node child= children.item(i);
            if(child instanceof Text && ((Text) child).getData().trim().length()>0)
                allTextchildrenAreWhitespace=false;
            else if (child instanceof Element){
                elements++;
                count+=removeElementContentWhiteSpace((Element) child);
            }
        }
        if (elements>0 && allTextchildrenAreWhitespace){
            for(int i= children.getLength()-1; i>=0; i--){
                Node child= children.item(i);
                if(child instanceof Text){
                    e.removeChild(child);
                    count++;
                }
            }
        }
        return count;
    }
    public Component get (String name){
        Component[] components= getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i].getName().equals(name)) 
                return components[i];
        }
        return null;
    }
    private void parseGridBag(Element documentElement) {
        NodeList rows= documentElement.getChildNodes();
        for (int i = 0; i < rows.getLength(); i++) {
            Element row= (Element) rows.item(i);
            NodeList cells= row.getChildNodes();
            for (int j=0; j<cells.getLength(); j++){
                Element cell= (Element) cells.item(j);
                parseCell(cell, i, j);
            }
        }
    }

    private void parseCell(Element cell, int i, int j) {
        String value= cell.getAttribute("gridx");
        if(value.length()==0){
            if(j==0) constrains.gridx=0;
            else constrains.gridx+=constrains.gridwidth;
        } else constrains.gridx=Integer.parseInt(value);
        value= cell.getAttribute("gridy");
        if(value.length()==0){
            constrains.gridy=i;
        } else constrains.gridy=Integer.parseInt(value);
        constrains.gridwidth= Integer.parseInt(cell.getAttribute("gridwidth"));
        constrains.gridheight=Integer.parseInt(cell.getAttribute("gridheight"));
        constrains.weightx= Integer.parseInt(cell.getAttribute("weightx"));
        constrains.weighty=Integer.parseInt(cell.getAttribute("weighty"));
        constrains.ipadx=Integer.parseInt(cell.getAttribute("ipadx"));
        constrains.ipady= Integer.parseInt(cell.getAttribute("ipady"));
        
        Class <GridBagConstraints> c1= GridBagConstraints.class;
        try {
            String name= cell.getAttribute("fill");
            Field f= c1.getField(name);
            constrains.fill= f.getInt(c1);
            name= cell.getAttribute("anchor");
            f= c1.getField(name);
            constrains.anchor= f.getInt(c1);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        Component comp= (Component) parseBean((Element) cell.getFirstChild());
        add(comp, constrains);
    }
    
    private Object parseBean (Element e){
        try {
            NodeList children= e.getChildNodes();
            Element classElement= (Element) children.item(0);
            String classname= ((Text) classElement.getFirstChild()).getData();
            Class <?> cl= Class.forName(classname);
            Object obj= cl.newInstance();
            if (obj instanceof Component)
                ((Component) obj).setName(e.getAttribute("id"));
            for (int i = 1; i < children.getLength(); i++) {
                Node propertyElement= children.item(i);
                Element nameElement= (Element) propertyElement.getFirstChild();
                String propertyName= ((Text) nameElement.getFirstChild()).getData();
                Element valueElement= (Element) propertyElement.getLastChild();
                Object value= parseValue(valueElement);
                BeanInfo beanInfo= Introspector.getBeanInfo(cl);
                PropertyDescriptor[] descriptors= beanInfo.getPropertyDescriptors();
                boolean done= false;
                for (int j = 0; !done && j<descriptors.length; j++) {
                    if(descriptors[j].getName().equals(propertyName)){
                        descriptors[j].getWriteMethod().invoke(obj, value);
                        done=true;
                    }
                }
            }
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Object parseValue(Element valueElement) {
        Element child= (Element) valueElement.getFirstChild();
        if(child.getTagName().equals("bean")) return parseBean(child);
        String text= ((Text) child.getFirstChild()).getData();
        if(child.getTagName().equals("int")) return new Integer(text);
        else if (child.getTagName().equals("boolean")) return new Boolean(text);
        else if (child.getTagName().equals("string")) return text;
        else return null;
    }
    
}
