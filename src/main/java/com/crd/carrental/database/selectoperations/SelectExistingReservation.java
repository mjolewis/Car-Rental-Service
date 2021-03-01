package com.crd.carrental.database.selectoperations;

import com.crd.carrental.controllers.ExistingReservationResponse;
import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.controllers.Response;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.rentalportfolio.storedata.Location;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**********************************************************************************************************************
 * Select reservation details the database when provided with a reservationId. This strategy allows a customer to
 * request information about an existing reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SelectExistingReservation extends SelectStrategy {

    public SelectExistingReservation() {
        this.con = OpenConnection.getInstance();
    }

    @Override
    public Response select(ReservationController controller) {
        this.reservationId = controller.getReservationId();
        Response existingReservationResponse = null;

        String selectStatement =
                "SELECT "
                    + "customer.first_name, "
                    + "customer.last_name, "
                    + "reservation.start, "
                    + "reservation.end, "
                    + "vehicle.manufacturer, "
                    + "vehicle.model, "
                    + "vehicle.daily_price, "
                    + "store.street_number, "
                    + "store.street_name, "
                    +"store.city, "
                    + "store.state, "
                    + "store.zip_code "
                + "FROM "
                    + "customer "
                        + "JOIN "
                    + "reservation ON customer.customer_id = reservation.customer_id "
                        + "JOIN "
                    + "vehicle ON reservation.vehicle_id = vehicle.vehicle_id "
                        + "JOIN "
                    + "store ON vehicle.store_id = store.store_id "
                + "WHERE reservation.reservation_id = ?;";

        try {
            createPreparedStatement(selectStatement);
            resultSet = executeQuery(pStmt);
            existingReservationResponse = isRecordFound();
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(resultSet, pStmt);

        return existingReservationResponse;

    }

    @Override
    public void createPreparedStatement(String selectStatement) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setString(1, reservationId);
    }

    @Override
    public Response createValidResponse(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Timestamp start = resultSet.getTimestamp("start");
        Timestamp end = resultSet.getTimestamp("end");
        String manufacturer = resultSet.getString("manufacturer");
        String model = resultSet.getString("model");
        BigDecimal dailyPrice = resultSet.getBigDecimal("daily_price");
        String streetNumber = resultSet.getString("street_number");
        String streetName = resultSet.getString("street_name");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        String zipCode = resultSet.getString("zip_code");

        return new ExistingReservationResponse(firstName, lastName, start, end, manufacturer, model,
                dailyPrice, streetNumber, streetName, city, state, zipCode);
    }

    @Override
    public Response createInvalidResponse() {
        return new ExistingReservationResponse(null, null, null, null, null,
                null, null, null, null, null, null, null);
    }
}
