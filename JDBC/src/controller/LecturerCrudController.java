package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LecturerCrudController {
    public static ArrayList<String> getLecturerId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT lec_id FROM Lecturer");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
}
