package chapter1;

/**
 * Created by v11424 on 24/09/2015.
 */
public class Manager extends Employee {
    private Employee secretary;

    public Manager(String name, int salary, int hire_year, int hire_month, int hire_day) {
        super(name, salary, hire_year, hire_month, hire_day);
        secretary=null;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return super.toString()+
                "[secretary=" + secretary +
                ']';
    }
}
