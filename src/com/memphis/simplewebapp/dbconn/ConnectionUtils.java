package com.memphis.simplewebapp.dbconn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        return MariaConnector.getConnection();
    }

    public static void closeQuietly(Connection conn) throws SQLException {
        conn.close();
    }

    public static void rollbackQuietly(Connection conn) throws SQLException {
        conn.rollback();
    }
}
