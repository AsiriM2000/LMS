package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchCrudController {
    public static ArrayList<String> getBatchName() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT batch_name FROM Batch");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
}
