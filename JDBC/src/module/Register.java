package module;

import java.time.LocalDate;

public class Register {
    private String register_id;
    private String course_name;
    private String batch_name;
    private String student_name;
    private String email;
    private String dob;
    private String address;
    private String city;
    private String ol;
    private String al;
    private double amount;

    public Register() {
    }

    public Register(String register_id, String course_name, String batch_name, String student_name, String email, String dob, String address, String city, String ol, String al, double amount) {
        this.register_id = register_id;
        this.course_name = course_name;
        this.batch_name = batch_name;
        this.student_name = student_name;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.ol = ol;
        this.al = al;
        this.amount = amount;
    }

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOl() {
        return ol;
    }

    public void setOl(String ol) {
        this.ol = ol;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Register{" +
                "register_id='" + register_id + '\'' +
                ", course_name='" + course_name + '\'' +
                ", batch_name='" + batch_name + '\'' +
                ", student_name='" + student_name + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", ol='" + ol + '\'' +
                ", al='" + al + '\'' +
                ", amount=" + amount +
                '}';
    }
}
