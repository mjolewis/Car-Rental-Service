package com.crd.carrental.database;

import com.crd.carrental.rentalportfolio.Car;
import com.crd.carrental.rentalportfolio.CarUnavailable;
import com.crd.carrental.rentalportfolio.RentalComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Select one car from the database. A reservation will be made against this car.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Select implements SelectStrategy{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet resultSet;

    public Select() {
        this.con = ConnectionCreator.getInstance();
    }

    /**
     * Gets a car from the database if there is one available that matches the requested car type and rental location.
     *
     * @return A car if one was found. Otherwise
     */
    @Override
    public RentalComponent select(String location, String carType) {
        RentalComponent reservedCar = null;

        String selectStatement = "SELECT * FROM cars " +
                "WHERE location LIKE ? " +
                "AND carType LIKE ? " +
                "LIMIT 1";

        try {
            createPreparedStatement(selectStatement, location, carType);
            executeQuery();
            reservedCar = createCarForReservationResponse(location, carType);
        } catch (SQLException e) {
            handleException(e);
        }

        ConnectionCloser.closeQuietly(resultSet, pStmt);

        return reservedCar;
    }

    // Prepared Statements help prevent SQL injection and efficiently execute the statement
    private void createPreparedStatement(String selectStatement, String location, String carType) throws SQLException {
        pStmt = con.prepareStatement(selectStatement);
        pStmt.setString(1, "%" + location + "%");
        pStmt.setString(2, carType);
    }

    private void executeQuery() throws SQLException {
        resultSet = pStmt.executeQuery();
    }

    private RentalComponent createCarForReservationResponse(String location, String carType) throws SQLException {

        if (resultSet.next()) {
            String vin = resultSet.getString("vin");
            boolean isRented = resultSet.getBoolean("isRented");
            return new Car(vin, location, carType, isRented);
        }
        return new CarUnavailable(location, carType);
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }
}
