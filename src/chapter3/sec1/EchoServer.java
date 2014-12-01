/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3.sec1;

import java.io.IOException;
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
public class EchoServer {
    public static void main(String[] args) throws IOException {
        try{
        ServerSocket server= new ServerSocket(8189);
        Socket incoming=server.accept();
        try {
            InputStream in=incoming.getInputStream();
            OutputStream out= incoming.getOutputStream();
            Scanner scan= new Scanner(in);
            PrintWriter writer= new PrintWriter(out, true);
            writer.println("Hello! Enter BYE to exit");
            boolean done= false;
            while (scan.hasNextLine()&&!done) {
                String line= scan.nextLine();
                writer.println("Echo: "+line);
                if (line.trim().equals("BYE"))
                    done=true;
            }
        }finally{
            incoming.close();
        }
    } 
        catch (Exception e) {
                e.printStackTrace();
        }
    }
}
