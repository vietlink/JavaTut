/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3.sec2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author NgoVietLinh
 */
public class InterruptibleSocketTest {
    static private Scanner scan;
    static private JButton interruptibleButton;
    static private JButton blockingButton;
    static private JButton cancelButton;
    static private JTextArea messages;
    static private TestServer server;
    static private Thread connectThread;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame= new InterruptibleSocketFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
            }
        });
    }
    static class InterruptibleSocketFrame extends JFrame{

        public InterruptibleSocketFrame() throws HeadlessException {
            setTitle("InterruptibleSocketTest");
            setSize(800,800);
            JPanel northPanel= new JPanel();
            add(northPanel, BorderLayout.NORTH);
            messages= new JTextArea();
            add(new JScrollPane(messages));
            interruptibleButton = new JButton("Interruptible");
            blockingButton= new JButton("Blocking");
            northPanel.add(interruptibleButton);
            northPanel.add(blockingButton);
            interruptibleButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    interruptibleButton.setEnabled(false);
                    blockingButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    connectThread= new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try{
                                connectInterruptibly();
                            } catch(Exception e){
                                messages.append("\nInterruptibleSocket.connectInterruptibly: "+e);
                            }
                        }
                    });
                    connectThread.start();
                }
            });
            
            blockingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    interruptibleButton.setEnabled(false);
                    blockingButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    connectThread= new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try{
                                connectBlocking();
                            }catch(Exception e){
                                messages.append("\nconnectBlocking: "+e);
                            }
                        }
                    });
                    connectThread.start();
                }
            });
            cancelButton= new JButton("cancel");
            cancelButton.setEnabled(false);
            northPanel.add(cancelButton);
            cancelButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    connectThread.interrupt();
                    cancelButton.setEnabled(false);
                }
            });
            server =new TestServer();
            new Thread(server).start();
        }
        
        public void connectBlocking () throws IOException{
            messages.append("Blocking:\n");
            Socket socket= new Socket("localhost", 8189);
            try {
                scan= new Scanner(socket.getInputStream());
                while(!Thread.currentThread().isInterrupted()){
                    messages.append("Reading");
                    if(scan.hasNextLine()){
                        String line= scan.nextLine();
                        messages.append(line);
                        messages.append("\n");
                    }
                }
            } finally {
                socket.close();
                EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        messages.append("Socket closed \n");
                        interruptibleButton.setEnabled(true);
                        blockingButton.setEnabled(true);
                    }
                });
            }
        }
        public void connectInterruptibly() throws IOException{
            messages.append("Interruptible:\n");
            SocketChannel channel= SocketChannel.open(new InetSocketAddress("localhost", 8189));
            try {
                scan= new Scanner(channel);
                while(!Thread.currentThread().isInterrupted()){
                    messages.append("Reading");
                    if(scan.hasNextLine()){
                        String line= scan.nextLine();
                        messages.append(line);
                        messages.append("\n");
                    }
                }
            } finally {
                channel.close();
                EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        messages.append("Channel close\n");
                        interruptibleButton.setEnabled(true);
                        blockingButton.setEnabled(true);
                    }
                });
            }
        }
    }
    static class TestServer implements Runnable{
    
    @Override
    public void run() {
        try {
            ServerSocket server= new ServerSocket(8189);
//            try {
                while(true){
                    Socket socket= server.accept();
                    Runnable r= new TestServerHandle(socket);
                    Thread t= new Thread(r);
                    t.start();
                }
//            } catch (Exception e) {
//            }
        } catch (Exception e) {
            messages.append("\nTestServer.run: "+e);
        }
    }
}
    static class TestServerHandle implements Runnable{
        private Socket incoming;
        private int counter;
        
        public TestServerHandle(Socket incoming) {
            this.incoming = incoming;
        }
        
        @Override
        public void run() {
            try {
                OutputStream out= incoming.getOutputStream();
                PrintWriter print= new PrintWriter(out, true);
                while(counter<=100){
                    counter++;
                    if(counter<=10) print.println(counter);
                    Thread.sleep(100);
                }
                incoming.close();
                messages.append("Closing server\n");
            } catch (Exception e) {
                messages.append("\nTestServerHandle.run: "+e);
            }
        }
        
    }
}
