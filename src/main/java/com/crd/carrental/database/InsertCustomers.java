package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Update the database record for the customers who have made a reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertCustomers {
    private Connection con;
    private PreparedStatement pStmt;

    public InsertCustomers() {
        this.con = CreateConnection.getInstance();
    }

    /**
     * Inserts into the customer table after the reservation has been confirmed by the customer.
     */
    public void update(ReservationController controller) {

        String sqlInsert = "INSERT INTO customers values(?, ?, ?, ?, ?)";

        try {
            createPreparedStatement(sqlInsert, controller);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        CloseConnection.closeQuietly(pStmt);
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String updateStatement, ReservationController controller) throws SQLException {

        pStmt = con.prepareStatement(updateStatement);
        pStmt.setString(1, controller.getFirstName());
        pStmt.setString(2, controller.getLastName());
        pStmt.setString(3, controller.getEmailAddress());
        pStmt.setString(4, controller.getReservationNumber());
        pStmt.setLong(5, controller.getCreditCardNumber());
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
