package com.crd.carrental.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Update the database record for the car represented by a unique vin number.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Update implements UpdateStrategy {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet resultSet;

    public Update() {
        this.con = ConnectionCreator.getInstance();
    }

    @Override
    public void update(String vin) {
        String updateStatement = "UPDATE cars "
                + "SET isRented = true"
                + "WHERE vin LIKE ?";

        try {
            createPreparedStatement(updateStatement, vin);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        ConnectionCloser.closeQuietly(resultSet, pStmt);
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String updateStatement, String vin) throws SQLException {
        pStmt = con.prepareStatement(updateStatement);
        pStmt.setString(1, "%" + vin + "%");
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
