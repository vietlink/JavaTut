/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chapter1;

/**
 *
 * @author NgoVietLinh
 */
public class Employee {
    private String name;
    private int salary;
    private int hire_year;
    private int hire_month;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getHire_year() {
        return hire_year;
    }

    public void setHire_year(int hire_year) {
        this.hire_year = hire_year;
    }

    public int getHire_month() {
        return hire_month;
    }

    public void setHire_month(int hire_month) {
        this.hire_month = hire_month;
    }

    public Employee(String name, int salary, int hire_year, int hire_month) {
        this.name = name;
        this.salary = salary;
        this.hire_year = hire_year;
        this.hire_month = hire_month;
    }
    
}
