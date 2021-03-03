package com.crd.carrental.database.insertoperations;

import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Inserts customer reservations into a MySQL database. These reservations are only inserted if there is no conflicting
 * reservation for the vehicle, start, end, and location attributes.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertReservation extends InsertStrategy {
    private Connection con;
    private PreparedStatement pstmt;

    public InsertReservation() {
        OpenConnection db = new OpenConnection();
        this.con = db.getDataSourceConnection();
    }

    /**
     * Assign the confirmed reservation number to a car that the customer requested. This allows an employee to
     * match a customers reservation number to a particular car when the customer shows up at the store.
     */
    @Override
    public void insert(NewReservationController controller) {
        String sqlInsert = "INSERT IGNORE INTO reservation VALUES(?, ?, ?, ?, ?)";

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
        pstmt.setString(1, controller.getReservationId());
        pstmt.setString(2, controller.getCustomerId());
        pstmt.setString(3, controller.getVehicleId());
        pstmt.setTimestamp(4, controller.getStart());
        pstmt.setTimestamp(5, controller.getEnd());
    }
}
