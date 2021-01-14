package com.crd.carrental.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**********************************************************************************************************************
 * Create a SQL table if it doesn't already exist. This table contains our customer information.
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
                        + "(firstName VARCHAR(20),"
                        + "lastName VARCHAR(20),"
                        + "emailAddress VARCHAR(20),"
                        + "reservationNumber VARCHAR(20), "
                        + "creditCardNumber long);");
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
