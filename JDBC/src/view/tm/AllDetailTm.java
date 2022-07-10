package view.tm;

import javafx.scene.control.Button;

public class AllDetailTm {
    private String register_id;
    private String student_name;
    private String course_name;
    private double month_fee;
    private double all_fee;
    private Button button;

    public AllDetailTm() {
    }

    public AllDetailTm(String register_id, String student_name, String course_name, double month_fee, double all_fee, Button button) {
        this.register_id = register_id;
        this.student_name = student_name;
        this.course_name = course_name;
        this.month_fee = month_fee;
        this.all_fee = all_fee;
        this.button = button;
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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public double getMonth_fee() {
        return month_fee;
    }

    public void setMonth_fee(double month_fee) {
        this.month_fee = month_fee;
    }

    public double getAll_fee() {
        return all_fee;
    }

    public void setAll_fee(double all_fee) {
        this.all_fee = all_fee;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "AllDetailTm{" +
                "register_id='" + register_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", month_fee=" + month_fee +
                ", all_fee=" + all_fee +
                ", button=" + button +
                '}';
    }
}
