/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author NgoVietLinh
 */
public class Employee implements Serializable {
    private String name;
    private double salary;
    private Date hireDay;
    public static final int NAME_SIZE=40;
    public static final int RECORD_SIZE=2*NAME_SIZE+8+4+4+4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    public void raiseSalary(double byPercent){
        double raise= salary*byPercent/100;
        salary+=raise;
    }
    public String toString(){
        return getClass().getName()
                +"[name= "+name
                +", salary= "+salary
                +", hireDay= "+hireDay+"]";
    }
    public Employee(String name, int salary, int hire_year, int hire_month, int hire_day) {
        this.name = name;
        this.salary = salary;
        GregorianCalendar calendar= new GregorianCalendar(hire_year, hire_month-1, hire_day);
        hireDay= calendar.getTime();
    }

    public Employee() {
    }

    public void writeData(DataOutput out) throws IOException{
        DataIO.writeFixedString(name, NAME_SIZE, out);
        out.writeDouble(salary);
        GregorianCalendar calendar= new GregorianCalendar();
        calendar.setTime(hireDay);
        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH)+1);
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void readData(DataInput in) throws IOException{
        name= DataIO.readFixedString(NAME_SIZE, in);
        salary= in.readDouble();
        int y= in.readInt();
        int m= in.readInt();
        int d= in.readInt();
        GregorianCalendar calendar= new GregorianCalendar(y, m-1, d);
        hireDay= calendar.getTime();
    }
}
