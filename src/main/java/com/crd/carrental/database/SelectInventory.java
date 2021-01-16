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
public class SelectInventory extends SelectStrategy {
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
     * requirements, then the customer then the UI will notify the customer and offer a chance to enter a new
     * reservation request.
     *
     * @note New reservation requests can not overlap existing reservations. The query represents an exclusive NOT
     *         BETWEEN statement.
     */
    @Override
    public ReservationResponse select(ReservationController controller) {

        this.location = controller.getLocation();
        this.carType = controller.getCarType();
        this.reservationStartDateAndTime = controller.getReservationStartDateAndTime();
        this.reservationEndDateAndTime = controller.getReservationEndDateAndTime();
        ReservationResponse reservationResponse = null;

        String selectStatement = "SELECT * FROM cars " +
                "WHERE location LIKE ? " +
                "AND carType = ? " +
                "AND (reservationStartDateAndTime NOT BETWEEN ? AND ? " +
                "AND reservationStartDateAndTIme <> ? AND reservationStartDateAndTIme <> ? ) " +
                "AND (reservationEndDateAndTime NOT BETWEEN ? AND ? " +
                "AND reservationEndDateAndTime <> ? AND reservationEndDateAndTime ? ) " +
                "LIMIT 1";

        try {
            createPreparedStatement(selectStatement);
            resultSet = executeQuery(pStmt);
            reservationResponse = getReservationResponse();
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(resultSet, pStmt);

        return reservationResponse;
    }

    private void createPreparedStatement(String selectStatement) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setObject(1, location, Types.JAVA_OBJECT);
        pStmt.setObject(2, carType, Types.JAVA_OBJECT);
        pStmt.setTimestamp(3, reservationStartDateAndTime);
        pStmt.setTimestamp(4, reservationEndDateAndTime);
        pStmt.setTimestamp(5, reservationStartDateAndTime);
        pStmt.setTimestamp(6, reservationEndDateAndTime);
        pStmt.setTimestamp(7, reservationStartDateAndTime);
        pStmt.setTimestamp(8, reservationEndDateAndTime);
        pStmt.setTimestamp(9, reservationStartDateAndTime);
        pStmt.setTimestamp(10, reservationEndDateAndTime);
    }

    private ReservationResponse getReservationResponse() throws SQLException {
        String vin = "";

        if (resultSet.next()) {
            vin = resultSet.getString("vin");
            StoreNames storeName = Enum.valueOf(StoreNames.class, resultSet.getString("storeName"));
            return new ReservationResponse(vin, storeName, true);
        }
        return new ReservationResponse(vin, null, false);
    }
}
