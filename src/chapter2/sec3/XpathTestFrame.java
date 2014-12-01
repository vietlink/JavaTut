/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter2.sec3;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author NgoVietLinh
 */
public class XpathTestFrame extends JFrame{
    private DocumentBuilder builder;
    private Document doc;
    private XPath path;
    private JTextField expression;
    private JTextField result;
    private JComboBox typeCombo;
    private JTextArea docText;

    public XpathTestFrame() throws HeadlessException {
        setTitle("XPathTest");
        setSize(400, 400);
        JMenu fileMenu= new JMenu("File");
        JMenuItem openItem= new JMenuItem("Open");
        JMenuItem exitItem= new JMenuItem("Exit");
        openItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        JMenuBar menuBar= new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        ActionListener listener= new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   evaluate();
            }
        };
        expression= new JTextField(20);
        expression.addActionListener(listener);
        JButton evaluateButton= new JButton("evaluate");
        evaluateButton.addActionListener(listener);
        typeCombo= new JComboBox(new Object[] {"STRING", "NODE", "NODESET", "NUMBER",
        "BOOLEAN"});
        typeCombo.setSelectedItem("STRING");
        
        JPanel panel= new JPanel();
        panel.add(expression);
        panel.add(typeCombo);
        panel.add(evaluateButton);
        docText= new JTextArea(10, 40);
        result= new JTextField();
        result.setBorder(new TitledBorder("Result"));
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(docText), BorderLayout.CENTER);
        add(result, BorderLayout.SOUTH);
        try {
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            builder= factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            JOptionPane.showMessageDialog(this, e);
        }
        XPathFactory xpfactory= XPathFactory.newInstance();
        path= xpfactory.newXPath();
        pack();
    }
    
    public void openFile(){
        JFileChooser chooser= new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
            return f.isDirectory()||f.getName().toLowerCase().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "XML file";
            }
        });
        int r= chooser.showOpenDialog(this);
        if (r!=JFileChooser.APPROVE_OPTION) return;
        File f= chooser.getSelectedFile();
        try {
            byte[] bytes= new byte[(int)f.length()];
            new FileInputStream(f).read(bytes);
            docText.setText(new String(bytes));
            doc= builder.parse(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (SAXException e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void evaluate(){
        try {
            String typeName= (String) typeCombo.getSelectedItem();
            QName returnType= (QName) XPathConstants.class.getField(typeName).get(null);
            //danh gia cau xpath
            Object evalResult= path.evaluate(expression.getText(), doc, returnType);
            if(typeName.equals("NODESET")){
                NodeList list= (NodeList) evalResult;
                StringBuilder builder= new StringBuilder();
                builder.append("{");
                for (int i = 0; i < list.getLength(); i++) {
                    if(i>0) builder.append(", ");
                    builder.append(""+list.item(i));
                }
                builder.append("}");
                result.setText(""+builder);
            }
            else result.setText(""+evalResult);
        } catch (XPathExpressionException e) {
            result.setText(""+e);
        } catch (Exception e){
            e.printStackTrace();
        }
                
    }
}
