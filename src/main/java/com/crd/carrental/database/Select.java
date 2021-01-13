package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationResponse;
import com.crd.carrental.rentalportfolio.*;

import java.sql.*;

/**********************************************************************************************************************
 * Select one car from the database. A reservation will be made against this car after the customer enters a
 * confirmation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Select implements SelectStrategy {
    private Connection con;
    private String location;
    private CarTypes carType;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;
    private PreparedStatement pStmt;
    private ResultSet resultSet;

    public Select() {
        this.con = ConnectionCreator.getInstance();
    }

    /**
     * Finds one car that matches the location and car type. If no car is available that matches the customer
     * requirements, then an CarUnavailable object is created and used to notify the customer that there are no cars
     * available that match the requirements.
     *
     * @note If a car is reserved, it can still be available because the existing reservation is in the future. This
     *         select statement will only find cars that have a reservation start date and time greater than the current
     *         customer's request. We do not allow reservations to be made against a car if the reservation end date and
     *         time is equal to the current customer's request because the company will perform routine cleaning services
     *         before the car can be rented again.
     */
    @Override
    public ReservationResponse select(String location, String carType, String reservationStartDateAndTime,
                                  String reservationEndDateAndTime) {

        this.location = location;
        this.carType = CarTypes.valueOf(carType);
        this.reservationStartDateAndTime = Timestamp.valueOf(reservationStartDateAndTime);
        this.reservationEndDateAndTime = Timestamp.valueOf(reservationEndDateAndTime);
        ReservationResponse reservationResponse = null;

        String selectStatement = "SELECT * FROM cars " +
                "WHERE location LIKE ? " +
                "AND carType LIKE ? " +
                "AND isAvailable LIKE ? " +
                "AND (reservationStartDateAndTime > ? OR reservationEndDateAndTime < ? )" +
                "LIMIT 1";

        try {
            createPreparedStatement(selectStatement);
            executeQuery();
            reservationResponse = getReservationResponse();
        } catch (SQLException e) {
            handleException(e);
        }

        ConnectionCloser.closeQuietly(resultSet, pStmt);

        return reservationResponse;
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String selectStatement) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setString(1, location);
        pStmt.setObject(2, carType, Types.JAVA_OBJECT);
        pStmt.setBoolean(3, true);
        pStmt.setTimestamp(4, reservationStartDateAndTime);
        pStmt.setTimestamp(5, reservationEndDateAndTime);
    }

    private void executeQuery() throws SQLException {
        resultSet = pStmt.executeQuery();
    }

    private ReservationResponse getReservationResponse() throws SQLException {
        String vin = "";

        if (resultSet.next()) {
            vin = resultSet.getString("vin");
            boolean isAvailable = resultSet.getBoolean("isAvailable");
            return new ReservationResponse(vin, carType, location, isAvailable);
        }

        return new ReservationResponse(vin, carType, location, false);
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
