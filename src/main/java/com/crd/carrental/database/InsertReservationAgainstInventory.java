package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Inserts customer reservations against the existing inventory of cars.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertReservationAgainstInventory extends InsertStrategy {
    private Connection con;
    private PreparedStatement pStmt;

    public InsertReservationAgainstInventory() {
        this.con = CreateConnection.getInstance();
    }

    /**
     * Assign the confirmed reservation number to a car that the customer requested. This allows an employee to
     * match a customers reservation number to a particular car when the customer shows up at the store.
     */
    @Override
    public void insert(ReservationController controller) {

        String sqlInsert = "INSERT INTO cars values(?, ?, ?, ?, ?, ?, ?)";

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
        pStmt.setString(1, controller.getVin());
        pStmt.setString(2, controller.getStoreName().toString());
        pStmt.setString(3, controller.getLocation().toString());
        pStmt.setString(4, controller.getCarType().toString());
        pStmt.setString(5, controller.getReservationNumber());
        pStmt.setTimestamp(6, controller.getReservationStartDateAndTime());
        pStmt.setTimestamp(7, controller.getReservationEndDateAndTime());
    }
}
