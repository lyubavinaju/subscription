package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConn {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static Connection connection() throws SQLException {
        Properties properties
                = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASS);
        return DriverManager.getConnection(DB_URL, properties);
    }





}
