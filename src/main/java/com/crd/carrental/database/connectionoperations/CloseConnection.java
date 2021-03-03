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

    public static void closeQuietly(ResultSet resultSet, PreparedStatement pstmt) {
        try {
            closeQuietly(resultSet);
        } finally {
            closeQuietly(pstmt);
        }
    }

    public static void closeQuietly(ResultSet resultSet) {
        try {
            close(resultSet);
        } catch (SQLException e) {
            // quiet
        }
    }

    public static void closeQuietly(PreparedStatement pstmt) {
        try {
            close(pstmt);
        } catch (SQLException e) {
            // quiet
        }
    }

    public static void closeQuietly(Connection con) {
        try {
            close(con);
        } catch (SQLException e) {
            // quiet
        }
    }

    private static void close(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    private static void close(PreparedStatement pstmt) throws SQLException {
        if (pstmt != null) {
            pstmt.close();
        }
    }

    private static void close(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
