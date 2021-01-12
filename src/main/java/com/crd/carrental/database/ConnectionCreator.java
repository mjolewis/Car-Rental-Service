package com.crd.carrental.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Create a thread safe connection to a SQL database using the Singleton pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ConnectionCreator {
    private static volatile Connection con;

    private ConnectionCreator() throws SQLException {
        con = DriverManager.getConnection(MySqlConfig.SQL_DB, MySqlConfig.SQL_USER, MySqlConfig.SQL_PWD);
    }

    public static Connection getInstance() {
        if (con == null) {
            synchronized (ConnectionCreator.class) {
                if (con == null) {
                    try {
                        new ConnectionCreator();
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
