package module;

public class Employee {
    private String employee_id;
    private String f_name;
    private String l_name;
    private String email;
    private String contact;
    private double salary;

    public Employee() {
    }

    public Employee(String employee_id, String f_name, String l_name, String email, String contact, double salary) {
        this.employee_id = employee_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.contact = contact;
        this.salary = salary;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                '}';
    }
}
