package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.controllers.ReservationResponse;
import com.crd.carrental.rentalportfolio.*;

import java.sql.*;

/**********************************************************************************************************************
 * Select one car from the database only if there is a car available.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SelectInventory implements SelectStrategy {
    private Connection con;
    private StoreLocations location;
    private CarTypes carType;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;
    private PreparedStatement pStmt;
    private ResultSet resultSet;

    public SelectInventory() {
        this.con = CreateConnection.getInstance();
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
    public ReservationResponse select(ReservationController controller) {

        this.location = controller.getLocation();
        this.carType = controller.getCarType();
        this.reservationStartDateAndTime = convertDateAndTime(controller.getReservationStartDateAndTime());
        this.reservationEndDateAndTime = convertDateAndTime(controller.getReservationEndDateAndTime());
        ReservationResponse reservationResponse = null;

        String selectStatement = "SELECT * FROM cars " +
                "WHERE location LIKE ? " +
                "AND carType = ? " +
                "AND isAvailable = ? " +
                "AND (reservationStartDateAndTime > ? OR reservationEndDateAndTime < ? " +
                "OR (reservationStartDateAndTime IS NULL AND reservationEndDateAndTime IS NULL))" +
                "LIMIT 1";

        try {
            createPreparedStatement(selectStatement);
            executeQuery();
            reservationResponse = getReservationResponse();
        } catch (SQLException e) {
            handleException(e);
        }

        CloseConnection.closeQuietly(resultSet, pStmt);

        return reservationResponse;
    }

    /**
     * The request can be fulfilled when either of the two scenarios below are true:
     * 1) An existing reservations start date and time is greater than the end date and time currently being requested.
     * 2) An existing reservations end date and time is less than the start date and time currently being requested.
     */
    private void createPreparedStatement(String selectStatement) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setObject(1, location, Types.JAVA_OBJECT);
        pStmt.setObject(2, carType, Types.JAVA_OBJECT);
        pStmt.setBoolean(3, true);
        pStmt.setTimestamp(4, reservationEndDateAndTime);
        pStmt.setTimestamp(5, reservationStartDateAndTime);
    }

    private Timestamp convertDateAndTime(String dateAndTime) {
        return Timestamp.valueOf(dateAndTime.replaceAll("T", " ") + ":00");
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
