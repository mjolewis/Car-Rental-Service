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
    private PreparedStatement pStmt;

    public InsertReservation() {
        this.con = OpenConnection.getInstance();
    }

    /**
     * Assign the confirmed reservation number to a car that the customer requested. This allows an employee to
     * match a customers reservation number to a particular car when the customer shows up at the store.
     */
    @Override
    public void insert(NewReservationController controller) {
        String sqlInsert = "INSERT INTO reservation VALUES(?, ?, ?, ?, ?)";

        try {
            createPreparedStatement(sqlInsert, controller);
            executeUpdate(pStmt);
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(pStmt);
    }

    private void createPreparedStatement(String insertStatement, NewReservationController controller) throws SQLException {
        pStmt = con.prepareStatement(insertStatement);
        pStmt.setString(1, controller.getReservationId());
        pStmt.setString(2, controller.getCustomerId());
        pStmt.setString(3, controller.getVehicleId());
        pStmt.setTimestamp(4, controller.getStart());
        pStmt.setTimestamp(5, controller.getEnd());
    }
}
