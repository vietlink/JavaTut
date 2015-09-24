/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter1;

import java.io.*;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author NgoVietLinh
 */
public class RandomAccess {
    public static void main(String[] args) throws IOException{
        Employee[] staff= new Employee[3];
        staff[0]= new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1]= new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2]= new Employee("Tony Tester", 40000, 1990, 3, 15);
        try (DataOutputStream out= new DataOutputStream(new FileOutputStream("employee.dat"))){
            for (Employee employee : staff) {
                employee.writeData(out);
            }
        }
        try(java.io.RandomAccessFile in= new RandomAccessFile("employee.dat","r")){
            int n= (int)(in.length()/Employee.RECORD_SIZE);
            Employee[] newStaff= new Employee[n];
            for (int i = n-1; i >=0 ; i--) {
                newStaff[i]= new Employee();
                in.seek(i*Employee.RECORD_SIZE);
                newStaff[i].readData(in);
            }
            in.close();
            for(Employee e:newStaff){
                System.out.println(e);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
