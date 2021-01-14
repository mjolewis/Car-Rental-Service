package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**********************************************************************************************************************
 * Update the database record for the customers who have made a reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class UpdateCustomersTable implements UpdateStrategy {
    private Connection con;
    private PreparedStatement pStmt;

    public UpdateCustomersTable() {
        this.con = ConnectionCreator.getInstance();
    }

    /**
     * Updates the customer database after the reservation has been confirmed by the customer.
     */
    @Override
    public void update(ReservationController controller) {

        String updateStatement = "UPDATE customers "
                + "SET firstName = ? "
                + "SET lastName = ? "
                + "SET emailAddress = ? "
                + "SET reservationNumber = ? "
                + "SET creditCardNumber = ?";

        try {
            createPreparedStatement(updateStatement, controller);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        ConnectionCloser.closeQuietly(pStmt);
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
