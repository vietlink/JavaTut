/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3;

import java.net.InetAddress;

/**
 *
 * @author NgoVietLinh
 */
public class InetAddressTest {
    public static void main(String[] args) {
        try {
            
                String host= "www.google.com";
                InetAddress[] addresses= InetAddress.getAllByName(host);
                for (InetAddress address : addresses) {
                    System.out.println(address);
                }
            
                InetAddress localhost= InetAddress.getLocalHost();
                System.out.println(localhost);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
