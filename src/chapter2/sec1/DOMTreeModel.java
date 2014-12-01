/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter2.sec1;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author NgoVietLinh
 */
public class DOMTreeModel implements TreeModel{
    private Document doc;
    public DOMTreeModel(Document doc) {
        this.doc=doc;
    }
    
    @Override
    public Object getRoot() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return doc.getDocumentElement();
    }

    @Override
    public Object getChild(Object parent, int index) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Node node= (Node) parent;
        NodeList list= node.getChildNodes();
        return list.item(index);
    }

    @Override
    public int getChildCount(Object parent) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Node node= (Node) parent;
        NodeList list= node.getChildNodes();
        return list.getLength();
    }

    @Override
    public boolean isLeaf(Object node) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return getChildCount(node)==0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
//        throw new UnsupportedOperationException("Not supported yet. 1"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Node node= (Node) parent;
        NodeList list= node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if(getChild(node, i)==child) return i;        
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet. 2"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet. 3"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
