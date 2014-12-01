/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter2.sec1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author NgoVietLinh
 */
class DOMTreeFrame extends JFrame{
        private static final int HEIGHT=400;
        private static final int WIDTH=400;
        private DocumentBuilder builder;
        
        public DOMTreeFrame() {
            setSize(WIDTH, HEIGHT);
            JMenu fileMenu= new JMenu("File");
            JMenuItem openItem= new JMenuItem("Open");
            openItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    openFile();
                }
            });
            fileMenu.add(openItem);
            JMenuItem exitItem= new JMenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            fileMenu.add(exitItem);
            JMenuBar menuBar= new JMenuBar();
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);
        }
        public void openFile(){
            JFileChooser chooser= new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory()||
                            f.getName().toLowerCase().endsWith(".xml");
                }

                @Override
                public String getDescription() {
                    return "XML file";
                }
            });
            int r= chooser.showOpenDialog(this);
            if (r!= JFileChooser.APPROVE_OPTION) return;
            final File f= chooser.getSelectedFile();
            new SwingWorker<Document, Void>(){
                @Override
                protected Document doInBackground() throws Exception{
                    if(builder==null){
                        DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
                        builder= factory.newDocumentBuilder();
                    }
                    return (Document) builder.parse(f);
                }

                @Override
                protected void done() {
                    try {
                        Document doc= get();
                        JTree tree= new JTree(new DOMTreeModel(doc));
                        tree.setCellRenderer(new DOMTreeCellRenderer());
                        setContentPane(new JScrollPane(tree));
                        validate();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(DOMTreeFrame.this, e);
                        
                    }
                }
                
            }.execute();
                
            }
            
        }
