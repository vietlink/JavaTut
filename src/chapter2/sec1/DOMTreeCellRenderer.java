/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter2.sec1;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 *
 * @author NgoVietLinh
 */
public class DOMTreeCellRenderer extends DefaultTreeCellRenderer{
    public Component getTreeCellRendererComponent (JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus){
        Node node= (Node)value;
        if (node instanceof Element) return elementPanel((Element) node);
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (node instanceof CharacterData)
            setText(characterString((CharacterData) node));
        else setText(node.getClass()+": "+ node.toString());
        return this;
    }

    private JPanel elementPanel(Element element) {
        JPanel panel= new JPanel();
        panel.add(new JLabel("Element: "+ element.getTagName()));
        final NamedNodeMap map= element.getAttributes();
        panel.add(new JTable(new AbstractTableModel(){

            @Override
            public int getRowCount() {
                return map.getLength();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex==0?map.item(rowIndex).getNodeName(): map.item(rowIndex).getNodeValue();
            }
            
        }));
        return panel;
    }

    public static String characterString(CharacterData characterData) {
        StringBuilder builder= new StringBuilder(characterData.getData());
        for (int i = 0; i < builder.length(); i++) {
            if(builder.charAt(i)=='\r'){
                builder.replace(i, i+1, "\\r");
                i++;
            }
            else if(builder.charAt(i)=='\n'){
                builder.replace(i, i+1, "\\n");
                i++;
            }
            else if (builder.charAt(i)=='\t'){
                builder.replace(i, i+1, "\\t");
                i++;
            }
        }
        if (characterData instanceof CDATASection) builder.insert(0, "CDATASection: ");
        else if (characterData instanceof Text) builder.insert(0, "Text: ");
        else if (characterData instanceof Comment) builder.insert(0, "Conmment: ");
        return builder.toString();
    }
    
}
