package module;

public class Payment {
    private String register_id;
    private String course_id;
    private String month;
    private double month_fee;


    public Payment() {
    }

    public Payment(String register_id, String course_id, String month, double month_fee) {
        this.register_id = register_id;
        this.course_id = course_id;
        this.month = month;
        this.month_fee = month_fee;
    }

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getMonth_fee() {
        return month_fee;
    }

    public void setMonth_fee(double month_fee) {
        this.month_fee = month_fee;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "register_id='" + register_id + '\'' +
                ", course_id='" + course_id + '\'' +
                ", month='" + month + '\'' +
                ", month_fee=" + month_fee +
                '}';
    }
}
