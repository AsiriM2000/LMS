package module;

public class Subject {
    private String course_id;
    private String subject_id;
    private String subject_name;

    public Subject() {
    }

    public Subject(String course_id, String subject_id, String subject_name) {
        this.course_id = course_id;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    @Override
    public String toString() {
        return "Subject{" +
                "course_id='" + course_id + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", subject_name='" + subject_name + '\'' +
                '}';
    }
}
