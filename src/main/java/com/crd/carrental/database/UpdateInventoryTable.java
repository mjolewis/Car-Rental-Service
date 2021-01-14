package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;

import java.sql.*;

/**********************************************************************************************************************
 * Update the database record for the car represented by a unique vin number.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class UpdateInventoryTable implements UpdateStrategy {
    private Connection con;
    private PreparedStatement pStmt;

    public UpdateInventoryTable() {
        this.con = CreateConnection.getInstance();
    }

    /**
     * Updates the database after the reservation has been confirmed by the customer. Importantly, the database
     * maintains the isReserved and isAvailable fields. If a reservation is confirmed by the customer, then the
     * isReserved field will be set to true. However, this car can still be available for rent if the reservation date
     * and time are in the future. As a result, the system will perform an operation against the
     * reservationStartDateAndTime to before updating the database with the correct value.
     */
    @Override
    public void update(ReservationController controller) {

        String updateStatement = "UPDATE cars " +
                "SET isReserved = ? " +
                "SET isAvailable = ? " +
                "SET reservationStartDateAndTime = ? " +
                "SET reservationEndDateAndTime = ? " +
                "WHERE vin = ?";

        try {
            createPreparedStatement(updateStatement, controller);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        CloseConnection.closeQuietly(pStmt);
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String updateStatement, ReservationController controller) throws SQLException {

        Timestamp convertedStartDateAndTime = convertDateAndTime(controller.getReservationStartDateAndTime());
        Timestamp convertedEndDateAndTime = convertDateAndTime(controller.getReservationStartDateAndTime());

        pStmt = con.prepareStatement(updateStatement);
        pStmt.setBoolean(1, true);
        pStmt.setBoolean(2, isCarAvailable());
        pStmt.setTimestamp(3, convertedStartDateAndTime);
        pStmt.setTimestamp(4, convertedEndDateAndTime);
        pStmt.setString(5, controller.getVin());
    }

    private Timestamp convertDateAndTime(String dateAndTime) {
        return Timestamp.valueOf(dateAndTime.replaceAll("T", " ") + ":00");
    }

    private boolean isCarAvailable() {
        Timestamp current = Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString());
        //Timestamp reservationTime = Timestamp.valueOf(reservationStartDateAndTime.toString());
        //int compare = current.compareTo(reservationTime);

        //return compare <= 0;
        return true;
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
