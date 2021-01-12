package com.crd.carrental.database;

import java.sql.*;

/**********************************************************************************************************************
 * Create a SQL table if it doesn't already exist.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class TableCreator implements TableStrategy {
    private Connection con;
    private Statement stmt;                                     // A statement object holds SQL commands

    public TableCreator() {
        this.con = ConnectionCreator.getInstance();
    }

    @Override
    public void createTableIfDoesntExist(String tableName) {
        try {
            stmt = createStatement();
            DatabaseMetaData metaData = getConnectionMetaData();
            ResultSet table = metaData.getTables(null, null, tableName, null);
            boolean tableExists = doesTableExist(table);
            if (!tableExists) {
                createTable(tableName);
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    private Statement createStatement() throws SQLException {
        return con.createStatement();
    }

    private DatabaseMetaData getConnectionMetaData() throws SQLException {
        return con.getMetaData();
    }

    private boolean doesTableExist(ResultSet table) throws SQLException {
        return table.next();
    }

    private void createTable(String tableName) throws SQLException {
        stmt.execute("CREATE TABLE " + tableName +
                "(vin VARCHAR(17) PRIMARY KEY," +
                "location VARCHAR(20)," +
                "carType VARCHAR(20)," +
                "isRented BOOLEAN, " +
                "reservationStartDate DATE, " +
                "reservationEndDate DATE);");
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}