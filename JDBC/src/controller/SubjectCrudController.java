package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectCrudController {
    public static ArrayList<String> getSubjectId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT subject_id FROM Subject");
        ArrayList<String> id= new ArrayList<>();
        while (result.next()){
            id.add(result.getString(1));
        }
        return id;
    }
}
