package module;

public class Lecturer {
    private String lec_id;
    private String f_name;
    private String l_name;
    private String gender;
    private String experience;
    private String city;


    public Lecturer() {
    }

    public Lecturer(String lec_id, String f_name, String l_name, String gender, String experience, String city) {
        this.lec_id = lec_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.gender = gender;
        this.experience = experience;
        this.city = city;
    }

    public String getLec_id() {
        return lec_id;
    }

    public void setLec_id(String lec_id) {
        this.lec_id = lec_id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "lec_id='" + lec_id + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", gender='" + gender + '\'' +
                ", experience='" + experience + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
