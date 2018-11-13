package com.ivanov.connection;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/calendar_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
}
