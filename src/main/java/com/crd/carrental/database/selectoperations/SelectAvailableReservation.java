package com.crd.carrental.database.selectoperations;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.controllers.NewReservationResponse;
import com.crd.carrental.controllers.Response;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.utils.Hasher;

import java.sql.*;

/**********************************************************************************************************************
 * Select one vehicle from the database while ensuring that the reservation request does not conflict with an existing
 * reservation in the MySQL database.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SelectAvailableReservation extends SelectStrategy {

    public SelectAvailableReservation() {
        this.con = OpenConnection.getInstance();
    }

    /**
     * Finds one vehicle that matches the customers desired attributes. The query ensures that the reservation request
     * does not conflict with an existing reservation in the database.
     */
    @Override
    public Response select(ReservationController controller) {
        this.city = controller.getCity();
        this.classification = controller.getClassification();
        this.start = controller.getStart();
        this.end = controller.getEnd();
        Response newReservationResponse = null;

        String selectStatement =
                "SELECT "
                    + "vehicle.vehicle_id, "
                    + "store.street_number, "
                    + "store.street_name, "
                    + "store.city, "
                    + "store.state, "
                    + "store.zip_code "
                + "FROM "
                    + "vehicle "
                        + "JOIN "
                    + "store ON vehicle.store_id = store.store_id "
                + "WHERE " +
                        "store.city = ? "
                            + "AND vehicle.classification = ? "
                            + "AND NOT EXISTS (SELECT "
                                + "reservation.reservation_id "
                            + "FROM "
                                + "reservation "
                                    + "RIGHT JOIN "
                                + "vehicle ON reservation.vehicle_id = vehicle.vehicle_id "
                                    + "RIGHT JOIN "
                                + "store ON store.store_id = vehicle.store_id "
                            + "WHERE "
                                + "store.city = ? "
                                    + "AND vehicle.classification = ? "
                                    + "AND (reservation.start < ? "
                                    + "AND (reservation.end > ?))) "
                + "LIMIT 1";

        try {
            createPreparedStatement(selectStatement);
            resultSet = executeQuery(pStmt);
            newReservationResponse = isRecordFound();
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(resultSet, pStmt);

        return newReservationResponse;
    }

    @Override
    public void createPreparedStatement(String selectStatement) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setString(1, city);
        pStmt.setString(2, classification);
        pStmt.setString(3, city);
        pStmt.setString(4, classification);
        pStmt.setTimestamp(5, end);
        pStmt.setTimestamp(6, start);
    }

    @Override
    public Response createValidResponse(ResultSet resultSet) throws SQLException {
        String reservationId = Hasher.getSaltString();
        String vehicleId = resultSet.getString("vehicle_id");
        String streetNumber = resultSet.getString("street_number");
        String streetName = resultSet.getString("street_name");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        String zipCode = resultSet.getString("zip_code");
        return new NewReservationResponse(reservationId, vehicleId, streetNumber,
                streetName, city, state, zipCode, true);
    }

    @Override
    public Response createInvalidResponse() {
        return new NewReservationResponse(null, null, null,
                null, null, null, null, false);
    }
}
