package com.crd.carrental.database.insertoperations;

import com.crd.carrental.database.connectionoperations.CloseConnection;
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
    private Connection con;
    private String tableName;
    private PreparedStatement pstmt;

    public InsertVehicles(Connection con, String tableName) {
        this.con = con;
        this.tableName = tableName;
    }

    public void insert(RentalComponent cars) {
        Iterator<RentalComponent> iterator = cars.createIterator();

        while (iterator.hasNext()) {
            RentalComponent component = iterator.next();

            if (component.isChild()) {                                // Only add cars to the database
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

        String sqlInsert = "INSERT IGNORE INTO " + tableName + " values(?, ?, ?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        pstmt = con.prepareStatement(sqlInsert);
        pstmt.setString(1, vehicle.getVehicleId());
        pstmt.setString(2, vehicle.getStoreId());
        pstmt.setBigDecimal(3, vehicle.getDailyPrice());
        pstmt.setString(4, vehicle.getClassification());
        pstmt.setString(5, vehicle.getManufacturer());
        pstmt.setString(6, vehicle.getModel());
        pstmt.setInt(7, vehicle.getNumberOfPassengers());
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
