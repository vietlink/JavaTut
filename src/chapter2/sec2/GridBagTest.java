/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter2.sec2;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author NgoVietLinh
 */
public class GridBagTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                String fileName= args.length==0 ?"G:\\Users\\NgoVietLinh\\Documents\\NetBeansProjects\\JavaTut\\src\\chapter2\\sec2\\fontdialog.xml":args[0];
                JFrame frame;
                frame = new FontFrame(fileName);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
            }
        });
    }
    static class FontFrame extends JFrame{
        private JComboBox face;
        private JComboBox size;
        private JCheckBox bold;
        private JCheckBox italic;
        private GridBagPane gridBag;
        public FontFrame(String title) throws HeadlessException {
            setSize(800, 800);
            setTitle("GridBagTest");
            gridBag= new GridBagPane(title);
            add(gridBag);
            face= (JComboBox) gridBag.get("face");
            size= (JComboBox) gridBag.get("size");
            bold= (JCheckBox) gridBag.get("bold");
            italic=(JCheckBox) gridBag.get("italic");
            face.setModel(new DefaultComboBoxModel(new Object[] 
            {"Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput"}));
            size.setModel(new DefaultComboBoxModel(new Object[]
            {"8", "10", "12", "15", "18", "24"}));
            ActionListener listener= new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setSample();
                }                                
            };
            face.addActionListener(listener);
            size.addActionListener(listener);
            bold.addActionListener(listener);
            italic.addActionListener(listener);
            setSample();
        }

        private void setSample() {
            String FontFace =(String) face.getSelectedItem();
            int fontSize= Integer.parseInt((String)size.getSelectedItem());
            JTextArea sample= (JTextArea) gridBag.get("sample");
            int fontStyle= (bold.isSelected()? Font.BOLD:0)+(italic.isSelected()? Font.ITALIC:0);
            sample.setFont(new Font(FontFace, fontStyle, fontSize));
            sample.repaint();
        }
        
    }
}
