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
                "SET isReserved = ?, " +
                "isAvailable = ?, " +
                "reservationStartDateAndTime = ?, " +
                "reservationEndDateAndTime = ? " +
                "WHERE vin = ?";

        try {
            createPreparedStatement(updateStatement, controller);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        CloseConnection.closeQuietly(pStmt);
    }

    private void createPreparedStatement(String updateStatement, ReservationController controller) throws SQLException {

        Timestamp convertedStartDateAndTime = convertDateAndTime(controller.getReservationStartDateAndTime());
        Timestamp convertedEndDateAndTime = convertDateAndTime(controller.getReservationEndDateAndTime());

        boolean isValidReservationDateAndTime = isStartAndEndValid(convertedStartDateAndTime, convertedEndDateAndTime);

        pStmt = con.prepareStatement(updateStatement);
        pStmt.setBoolean(1, true);
        pStmt.setBoolean(2, isValidReservationDateAndTime);
        pStmt.setTimestamp(3, convertedStartDateAndTime);
        pStmt.setTimestamp(4, convertedEndDateAndTime);
        pStmt.setString(5, controller.getVin());
    }

    private Timestamp convertDateAndTime(String dateAndTime) {
        return Timestamp.valueOf(dateAndTime.replaceAll("T", " ") + ":00");
    }

    // A valid reservation request can only happen if the date and time are in the future and if the end date and time
    // are greater than the start date and time
    private boolean isStartAndEndValid(Timestamp startDateAndTime, Timestamp endDateAndTime) {
        Timestamp current = Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString());

        return startDateAndTime.compareTo(endDateAndTime) <= 0 && current.compareTo(startDateAndTime) <= 0;
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
