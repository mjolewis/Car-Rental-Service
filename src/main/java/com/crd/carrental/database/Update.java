package com.crd.carrental.database;

import java.sql.*;

/**********************************************************************************************************************
 * Update the database record for the car represented by a unique vin number.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Update implements UpdateStrategy {
    private Connection con;
    private PreparedStatement pStmt;
    private String vin;
    private Timestamp currentDateAndTime;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;
    private boolean isCarAvailable;

    public Update() {
        this.con = ConnectionCreator.getInstance();
    }

    /**
     * Updates the database after the reservation has been confirmed by the customer. Importantly, the database
     * maintains the isReserved and isAvailable fields. If a reservation is confirmed by the customer, then the
     * isReserved field will be set to true. However, this car can still be available for rent if the reservation date
     * and time are in the future. As a result, the system will perform an operation against the
     * reservationStartDateAndTime to before updating the database with the correct value.
     */
    @Override
    public void update(String vin, String reservationStartDateAndTime, String reservationEndDateAndTime) {
        this.vin = vin;
        currentDateAndTime = getCurrentDateAndTime();
        this.reservationStartDateAndTime = Timestamp.valueOf(reservationStartDateAndTime);
        this.reservationEndDateAndTime = Timestamp.valueOf(reservationEndDateAndTime);
        isCarAvailable = isCarAvailable();

        String updateStatement = "UPDATE cars " +
                "SET isReserved = ? " +
                "SET isAvailable = ? " +
                "SET reservationStartDateAndTime = ? " +
                "SET reservationEndDateAndTime = ? " +
                "WHERE vin LIKE ?";

        try {
            createPreparedStatement(updateStatement);
            executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }

        ConnectionCloser.closeQuietly(pStmt);
    }

    private Timestamp getCurrentDateAndTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    private boolean isCarAvailable() {
        return currentDateAndTime.getTime() < this.reservationStartDateAndTime.getTime();
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String updateStatement) throws SQLException {
        pStmt = con.prepareStatement(updateStatement);
        pStmt.setBoolean(1, true);
        pStmt.setBoolean(2, isCarAvailable);
        pStmt.setTimestamp(3, reservationStartDateAndTime);
        pStmt.setTimestamp(4, reservationEndDateAndTime);
        pStmt.setString(5, vin);
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
