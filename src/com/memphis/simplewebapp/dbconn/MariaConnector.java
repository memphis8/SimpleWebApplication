package com.memphis.simplewebapp.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaConnector {


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String connUrl = "jdbc:mysql://localhost:3306/test";
        String name = "root";
        String pass = "toor";
        return getConnection(connUrl,name,pass);
    }

    public static Connection getConnection(String connectionUrl, String name, String pass) throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(connectionUrl, name, pass);
    }

}
