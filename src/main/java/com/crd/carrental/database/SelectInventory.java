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
     * Finds one car that matches the location and car type only when the reservation request does not overlap with an
     * existing reservation for a car that matches the requested type and location.
     */
    @Override
    public ReservationResponse select(ReservationController controller) {

        this.location = controller.getLocation();
        this.carType = controller.getCarType();
        this.reservationStartDateAndTime = controller.getReservationStartDateAndTime();
        this.reservationEndDateAndTime = controller.getReservationEndDateAndTime();
        ReservationResponse reservationResponse = null;

        String selectStatement = "SELECT * FROM cars "
                + "WHERE location = ? "
                + "AND carType = ? "
                + "AND NOT EXISTS "
                + "(SELECT * FROM cars "
                + "WHERE location = ? "
                + "AND carType = ? "
                + "AND (reservationStartDateAndTime < ? "
                + "AND (reservationEndDateAndTime > ? ))) "
                + "LIMIT 1";

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
        pStmt.setObject(3, location, Types.JAVA_OBJECT);
        pStmt.setObject(4, carType, Types.JAVA_OBJECT);
        pStmt.setTimestamp(5, reservationEndDateAndTime);
        pStmt.setTimestamp(6, reservationStartDateAndTime);
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
