package com.crd.carrental.database.selectoperations;

import com.crd.carrental.controllers.DataTransferObject;
import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.controllers.NewReservationDataTransferObject;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.utils.Hasher;
import java.sql.ResultSet;
import java.sql.SQLException;


/**********************************************************************************************************************
 * Select one vehicle from the database while ensuring that the reservation request does not conflict with an existing
 * reservation in the MySQL database.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SelectAvailableReservation extends SelectStrategy {

    public SelectAvailableReservation() {
        this.con = OpenConnection.getDataSourceConnection();
    }

    /**
     * Finds one vehicle that matches the customers desired attributes. The query ensures that the reservation request
     * does not conflict with an existing reservation in the database.
     */
    @Override
    public DataTransferObject select(NewReservationController controller) {
        this.city = controller.getCity();
        this.classification = controller.getClassification();
        this.start = controller.getStart();
        this.end = controller.getEnd();
        DataTransferObject dataTransferObject = null;

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
                + "WHERE "
                        + "store.city = ? "
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
            resultSet = executeQuery(pstmt);
            dataTransferObject = (resultSet.next()) ? createValidResponse(resultSet) : createInvalidResponse();
        } catch (SQLException e) {
            handleException(e);
        }

        closePreparedStatement(resultSet, pstmt);

        return dataTransferObject;
    }

    @Override
    public void createPreparedStatement(String selectStatement) throws SQLException {
        pstmt = con.prepareStatement(selectStatement);
        pstmt.setString(1, city);
        pstmt.setString(2, classification);
        pstmt.setString(3, city);
        pstmt.setString(4, classification);
        pstmt.setTimestamp(5, end);
        pstmt.setTimestamp(6, start);
    }

    @Override
    public DataTransferObject createValidResponse(ResultSet resultSet) throws SQLException {
        String reservationId = Hasher.getTwentyCharacterSaltString();
        String vehicleId = resultSet.getString("vehicle_id");
        String streetNumber = resultSet.getString("street_number");
        String streetName = resultSet.getString("street_name");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        String zipCode = resultSet.getString("zip_code");
        return new NewReservationDataTransferObject(reservationId, vehicleId, streetNumber,
                streetName, city, state, zipCode, true);
    }

    @Override
    public DataTransferObject createInvalidResponse() {
        return new NewReservationDataTransferObject(null, null, null,
                null, null, null, null, false);
    }
}
