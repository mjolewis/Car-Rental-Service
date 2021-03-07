package com.crd.carrental.database.insertoperations;

import com.crd.carrental.database.connectionoperations.CloseConnection;
import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.rentalportfolio.components.RentalComponent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**********************************************************************************************************************
 * Inserts individual vehicles into a MySQL database.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertVehicles {
    private static final String TABLE_NAME = "vehicle";
    private Connection con;
    private PreparedStatement pstmt;

    public InsertVehicles() {
        this.con = OpenConnection.getDataSourceConnection();
    }

    public void insert(RentalComponent vehicles) {
        Iterator<RentalComponent> iterator = vehicles.createIterator();

        while (iterator.hasNext()) {
            RentalComponent component = iterator.next();

            if (component.isChild()) {                                // Only add vehicles to the database
                try {
                    createPreparedStatement(component);
                    executeUpdate();
                } catch (SQLException e) {
                    handleException(e);
                }
            }
        }

        closePreparedStatement(pstmt);
    }

    private void createPreparedStatement(RentalComponent vehicle) throws SQLException {

        String sqlInsert = "INSERT IGNORE INTO " + TABLE_NAME + " values(?, ?, ?, ?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        pstmt = con.prepareStatement(sqlInsert);
        pstmt.setString(1, vehicle.getVehicleId());
        pstmt.setString(2, vehicle.getStoreId());
        pstmt.setBigDecimal(3, vehicle.getDailyPrice());
        pstmt.setString(4, vehicle.getClassification());
        pstmt.setString(5, vehicle.getManufacturer());
        pstmt.setString(6, vehicle.getModel());
        pstmt.setInt(7, vehicle.getNumberOfPassengers());
        pstmt.setLong(8, vehicle.getVersion());
    }

    private void executeUpdate() throws SQLException {
        pstmt.executeUpdate();
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }

    private void closePreparedStatement(PreparedStatement pstmt) {
        CloseConnection.closeQuietly(pstmt);
    }
}
