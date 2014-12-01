/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author NgoVietLinh
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        try{
            Socket socket= new Socket("chemuanang.com", 8080);
            
            try {
                InputStream in= socket.getInputStream();
                Scanner scan= new Scanner(in);
                    while(scan.hasNextLine()){
                        String line= scan.nextLine();
                        System.out.println(line);
                    }            
                }
            finally{
                socket.close();
            }
        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }
}
