package db;

import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection = null;
    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Learning_Management_System", "root", "");
    }


    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return (dbConnection==null)? dbConnection= new DBConnection(): dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public static AnchorPane anchorPane;
}
