package com.crd.carrental.database.connectionoperations;

import com.crd.carrental.config.ApplicationConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Create a thread safe connection to a SQL database using the Singleton pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class OpenConnection {
    private static volatile Connection con;

    private OpenConnection() throws SQLException {
        con = DriverManager.getConnection(
                ApplicationConfig.SQL_DB,
                ApplicationConfig.SQL_USER,
                ApplicationConfig.SQL_PWD);
    }

    public static Connection getInstance() {
        if (con == null) {
            synchronized (OpenConnection.class) {
                if (con == null) {
                    try {
                        new OpenConnection();
                    } catch (SQLException e) {
                        handleException(e);
                    }
                }
            }
        }
        return con;
    }

    private static void handleException(SQLException e) {
        e.printStackTrace();
    }
}
