package com.crd.carrental.database;

import java.sql.*;

/**********************************************************************************************************************
 * Create a SQL table for our inventory of cars if it doesn't already exist.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CreateInventoryTable extends CreateTableStrategy {
    private Connection con;

    public CreateInventoryTable(Connection con) {
        this.con = con;
    }

    @Override
    public void createTable(String tableName) {
        try {
            Statement stmt = getStatementIfTableDoesntExist(con, tableName);

            if (stmt != null) {
                stmt.execute("CREATE TABLE " + tableName
                        + "(vin VARCHAR(17),"
                        + "storeName VARCHAR(20),"
                        + "location VARCHAR(20),"
                        + "carType VARCHAR(20),"
                        + "reservationNumber VARCHAR(20), "
                        + "reservationStartDateAndTime TIMESTAMP, "
                        + "reservationEndDateAndTime TIMESTAMP);");
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}