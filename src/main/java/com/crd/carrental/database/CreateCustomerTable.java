package com.crd.carrental.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**********************************************************************************************************************
 * Create a SQL table for our customers if it doesn't already exist.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CreateCustomerTable extends CreateTableStrategy {
    private Connection con;

    public CreateCustomerTable(Connection con) {
        this.con = con;
    }

    @Override
    public void createTable(String tableName) {
        try {
            Statement stmt = getStatementIfTableDoesntExist(con, tableName);

            if (stmt != null) {
                stmt.execute("CREATE TABLE " + tableName
                        + "(fName VARCHAR(20),"
                        + "lName VARCHAR(20),"
                        + "email VARCHAR(20),"
                        + "reservationNumber VARCHAR(20), "
                        + "creditCard long);");
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
