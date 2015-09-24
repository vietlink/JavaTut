package chapter1;

import java.io.*;

/**
 * Created by v11424 on 24/09/2015.
 */
public class ObjectStreamTest {
    public static void main(String[] args) {
        Employee harry=new Employee("Harry", 50000, 1989, 10, 1);
        Manager carl= new Manager("Carl", 8000, 1987, 12, 2);
        carl.setSecretary(harry);
        Manager tony= new Manager("Tony", 50032, 1990, 3, 15);
        tony.setSecretary(harry);
        Employee[] staff= new Employee[3];
        staff[0]=harry;
        staff[1]=carl;
        staff[2]=tony;

        try {
            ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("employee.dat"));
            out.writeObject(staff);
            out.close();
            ObjectInputStream in= new ObjectInputStream(new FileInputStream("employee.dat"));
            Employee[] newStaff= (Employee[]) in.readObject();
            in.close();
            newStaff[1].raiseSalary(10);
            for (Employee e: newStaff)
                System.out.println(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
