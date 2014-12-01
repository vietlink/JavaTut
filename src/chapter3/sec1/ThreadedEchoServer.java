/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3.sec1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author NgoVietLinh
 */
public class ThreadedEchoServer {
    public static void main(String[] args) {
        try {
            int i=1;
            ServerSocket socket= new ServerSocket(8189);
            while (true) {                
                Socket incoming= socket.accept();
                System.out.println("Spawning: "+i);
                Runnable r= new ThreadedEchoHandle(incoming);
                Thread t= new Thread(r);
                t.start();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class ThreadedEchoHandle implements Runnable{
        private Socket incoming;
        public ThreadedEchoHandle(Socket i) {
            incoming= i;
        }

        @Override
        public void run() {
            try {
                try {
                    InputStream in= incoming.getInputStream();
                    OutputStream out= incoming.getOutputStream();
                    Scanner scan= new Scanner(in);
                    PrintWriter writer= new PrintWriter(out, true);
                    writer.println("Hello, enter BYE to exit");
                    boolean done= false;
                    while(!done&&scan.hasNextLine()){
                        String line= scan.nextLine();
                        writer.println(line);
                        if(line.trim().equals("BYE")) done=true;
                    }
                } finally {
                    incoming.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
}
