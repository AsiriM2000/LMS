package module;

public class Lecture {
    private String batch_name;
    private String subject_id;
    private String lec_id;
    private String date;
    private String time;

    public Lecture() {
    }

    public Lecture(String batch_name, String subject_id, String lec_id, String date, String time) {
        this.batch_name = batch_name;
        this.subject_id = subject_id;
        this.lec_id = lec_id;
        this.date = date;
        this.time = time;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getLec_id() {
        return lec_id;
    }

    public void setLec_id(String lec_id) {
        this.lec_id = lec_id;
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
        return "Lecture{" +
                "batch_name='" + batch_name + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", lec_id='" + lec_id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
