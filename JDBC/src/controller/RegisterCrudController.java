package controller;

import module.Register;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterCrudController {
    public static ArrayList<String> getRegisterIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT register_id FROM Register");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Register getRegister(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Register WHERE register_id =?", id);
        if (result.next()){
            return new Register(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10),
                    result.getDouble(11)

            );
        }
        return null;
    }
}
