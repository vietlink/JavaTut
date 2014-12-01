/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter1;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author NgoVietLinh
 */
public class RandomAccess {
    public static void main(String[] args) throws IOException{
        Employee[] staff= new Employee[3];
        staff[0]= new Employee("Carl Cracker", 75000, 1987, 12);
        staff[1]= new Employee("Harry Hacker", 50000, 1989, 10);
        staff[2]= new Employee("Tony Tester", 40000, 1990, 3);
        try (DataOutputStream out= new DataOutputStream(new FileOutputStream("employee.dat"))){
            for (Employee employee : staff) {
                write
            }
        }
    }
    public void writeData(DataOutput out, Employee e) throws IOException{
        DataIO.writeFixedString(e.getName(), Employee.N)
    }
}
