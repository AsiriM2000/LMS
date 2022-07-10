package module;

public class Attendant {
    private String register_id;
    private String student_name;
    private String subject_id;
    private String subject_name;
    private String date;
    private String time;

    public Attendant() {
    }

    public Attendant(String register_id, String student_name, String subject_id, String subject_name, String date, String time) {
        this.register_id = register_id;
        this.student_name = student_name;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.date = date;
        this.time = time;
    }

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Attendant{" +
                "register_id='" + register_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
