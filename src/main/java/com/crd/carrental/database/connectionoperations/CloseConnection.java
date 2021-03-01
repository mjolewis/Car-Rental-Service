package com.crd.carrental.database.connectionoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************************************************************************************************
 * A collection of JDBC helper methods to close connections. Utility classes should not have a public or default
 * constructor.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CloseConnection {

    public static void closeQuietly(ResultSet resultSet, PreparedStatement pStmt) {
        try {
            closeQuietly(resultSet);
        } finally {
            closeQuietly(pStmt);
        }
    }

    public static void closeQuietly(ResultSet resultSet) {
        try {
            close(resultSet);
        } catch (SQLException e) {
            // quiet
        }
    }

    private static void close(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closeQuietly(PreparedStatement pStmt) {
        try {
            close(pStmt);
        } catch (SQLException e) {
            // quiet
        }
    }

    private static void close(PreparedStatement pStmt) throws SQLException {
        if (pStmt != null) {
            pStmt.close();
        }
    }

    public static void closeQuietly(Connection con) {
        try {
            close(con);
        } catch (SQLException e) {
            // quiet
        }
    }

    private static void close(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
