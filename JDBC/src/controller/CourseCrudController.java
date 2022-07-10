package controller;

import module.Course;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseCrudController {
    public static ArrayList<String> getCourseIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT course_id FROM Course");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static ArrayList<String> getCourseNames() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT course_name FROM Course");
        ArrayList<String> names = new ArrayList<>();
        while (resultSet.next()){
            names.add(resultSet.getString(1));
        }
        return names;
    }
    public static Course getCourse(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Course WHERE course_id =?", id);
        if (result.next()){
            return new Course(
                    result.getString(1),
                    result.getString(2)
            );
        }
        return null;
    }
}
