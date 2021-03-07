package com.crd.carrental.database.insertoperations;

import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Update the database record for the customers who have made a reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertCustomerStrategy extends InsertStrategy {
    private Connection con;
    private PreparedStatement pstmt;

    public InsertCustomerStrategy() {
        this.con = OpenConnection.getDataSourceConnection();
    }

    /**
     * Inserts into the customer table after the reservation has been confirmed by the customer.
     */
    @Override
    public void insert(NewReservationController controller) {

        String sqlInsert = "INSERT IGNORE INTO customer values(?, ?, ?, ?)";

        try {
            createPreparedStatement(sqlInsert, controller);
            executeUpdate(pstmt);
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(pstmt);
    }

    private void createPreparedStatement(String insertStatement, NewReservationController controller)
            throws SQLException {

        pstmt = con.prepareStatement(insertStatement);
        pstmt.setString(1, controller.getCustomerId());
        pstmt.setString(2, controller.getFirstName());
        pstmt.setString(3, controller.getLastName());
        pstmt.setString(4, controller.getCreditCardNumber());
    }
}
