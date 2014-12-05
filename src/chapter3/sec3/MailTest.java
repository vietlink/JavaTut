/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3.sec3;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author v11424
 */
public class MailTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame= new MailTestFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class MailTestFrame extends JFrame{
    private Scanner scan;
    private PrintWriter writer;
    private JTextField from;
    private JTextField to;
    private JTextField smtpServer;
    private JTextArea message;
    private JTextArea comm;
    public MailTestFrame() throws HeadlessException {
        setSize(400, 400);
        setTitle("MailTestFrame");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();
        JLabel fromLabel= new JLabel("From:");
        gbc.gridx=gbc.gridy=0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(fromLabel, gbc);
        JLabel toLabel= new JLabel("To: ");
        gbc.gridx=0;gbc.gridy=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(toLabel, gbc);
        JLabel smtpLabel= new JLabel("SMTP Server");
        gbc.gridx=0; gbc.gridy=2;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(smtpLabel, gbc);
        from= new JTextField(20);    
        gbc.gridx=1; gbc.gridy=0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=20; gbc.weighty=0;
        add(from, gbc);
        to= new JTextField(20);  
        gbc.gridx=1; gbc.gridy=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=20; gbc.weighty=0;
        add(to, gbc);
        smtpServer= new JTextField(20);        
        gbc.gridx=1; gbc.gridy=2;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=20; gbc.weighty=0;
        add(smtpServer, gbc);
        message= new JTextArea(10, 20);
        gbc.gridx=0; gbc.gridy=3;
        gbc.gridheight=1; gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.BOTH;
        add(new JScrollPane(message), gbc);
        comm= new JTextArea(5, 20);
        gbc.gridx=0; gbc.gridy=4;
        gbc.gridheight=1; gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.BOTH;
        add(new JScrollPane(comm), gbc);
        JButton sendButton= new JButton("send");
        JPanel buttonPanel= new JPanel();
        buttonPanel.add(sendButton);
        gbc.gridx=0; gbc.gridy=5;
        gbc.gridheight=1; gbc.gridwidth=2;
        add(buttonPanel, gbc);
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        comm.setText("");
                        sendMail();
                        return null;
                    }
                }.execute();
            }
        });
    }
    public void recieve(){
        String line= scan.nextLine();
        comm.append(line);
        comm.append("\n");
    }
    public void send(String s){
        comm.append(s);
        comm.append("\n");
        writer.println(s.replaceAll("\n", "\r\n"));
        writer.flush();
    }
    public void sendMail(){
        try {
            Socket s= new Socket(smtpServer.getName(), 25);
            InputStream in= s.getInputStream();
            OutputStream out= s.getOutputStream();
            scan= new Scanner(in);
            writer= new PrintWriter(out, true);
            String hostName= InetAddress.getLocalHost().getHostName();
            recieve();
            send("HELLO"+ hostName);
            recieve();
            send("MAIL FROM: <"+from.getText()+">");
            recieve();
            send("RCPT TO: <"+ to.getText()+">");
            recieve();
            send("DATA");
            recieve();
            send(message.getText());
            recieve();
            send(".");
            recieve();
            s.close();
        } catch (Exception e) {
            comm.append("Error"+e);
        }
    }
    
    
}