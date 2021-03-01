package com.crd.carrental.database.insertoperations;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.database.connectionoperations.OpenConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Update the database record for the customers who have made a reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertCustomer extends InsertStrategy {
    private Connection con;
    private PreparedStatement pStmt;

    public InsertCustomer() {
        this.con = OpenConnection.getInstance();
    }

    /**
     * Inserts into the customer table after the reservation has been confirmed by the customer.
     */
    @Override
    public void insert(ReservationController controller) {

        String sqlInsert = "INSERT INTO customer values(?, ?, ?, ?)";

        try {
            createPreparedStatement(sqlInsert, controller);
            executeUpdate(pStmt);
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(pStmt);
    }

    private void createPreparedStatement(String insertStatement, ReservationController controller) throws SQLException {

        pStmt = con.prepareStatement(insertStatement);
        pStmt.setString(1, controller.getCustomerId());
        pStmt.setString(2, controller.getFirstName());
        pStmt.setString(3, controller.getLastName());
        pStmt.setString(4, controller.getCreditCardNumber());
    }
}
