package module;

public class Student {
    private String f_name;
    private String l_name;
    private String email;
    private String gender;
    private String contact;

    public Student() {
    }

    public Student(String f_name, String l_name, String email, String gender, String contact) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.gender = gender;
        this.contact = contact;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Student{" +
                "f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
