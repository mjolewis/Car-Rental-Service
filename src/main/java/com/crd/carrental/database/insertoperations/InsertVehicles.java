package com.crd.carrental.database.insertoperations;

import com.crd.carrental.database.connectionoperations.CloseConnection;
import com.crd.carrental.rentalportfolio.components.RentalComponent;

import java.sql.*;
import java.util.Iterator;

/**********************************************************************************************************************
 * Inserts individual vehicles into a MySQL database.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertVehicles {
    private Connection con;
    private String tableName;
    PreparedStatement pStmt;

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

        closePreparedStatement(pStmt);
    }

    private void createPreparedStatement(RentalComponent vehicle) throws SQLException {

        String sqlInsert = "INSERT IGNORE INTO " + tableName + " values(?, ?, ?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        pStmt = con.prepareStatement(sqlInsert);
        pStmt.setString(1, vehicle.getVehicleId());
        pStmt.setString(2, vehicle.getStoreId());
        pStmt.setBigDecimal(3, vehicle.getDailyPrice());
        pStmt.setString(4, vehicle.getClassification());
        pStmt.setString(5, vehicle.getManufacturer());
        pStmt.setString(6, vehicle.getModel());
        pStmt.setInt(7, vehicle.getNumberOfPassengers());
    }

    private void executeUpdate() throws SQLException {
        pStmt.executeUpdate();
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }

    private void closePreparedStatement(PreparedStatement pStmt) {
        CloseConnection.closeQuietly(pStmt);
    }
}
