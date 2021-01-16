package com.crd.carrental.database;

import com.crd.carrental.rentalportfolio.RentalComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;

/**********************************************************************************************************************
 * Inserts individual cars into a MySQL database.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class InsertNewInventory implements InsertStrategy {
    private Connection con;
    private String tableName;
    PreparedStatement pStmt;

    public InsertNewInventory(Connection con, String tableName) {
        this.con = con;
        this.tableName = tableName;
    }

    @Override
    public void insert(RentalComponent cars) {
        Iterator<RentalComponent> iterator = cars.createIterator();

        while (iterator.hasNext()) {
            RentalComponent car = iterator.next();

            if (car.isChild()) {                                // Only add cars to the database
                try {
                    createPreparedStatement(car);
                    executeUpdate();
                } catch (SQLException e) {
                    handleException(e);
                }
            }
        }

        closePreparedStatement(pStmt);
    }

    private void createPreparedStatement(RentalComponent car) throws SQLException {

        String sqlInsert = "INSERT IGNORE INTO " + tableName + " values(?, ?, ?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        pStmt = con.prepareStatement(sqlInsert);
        pStmt.setString(1, car.getVin());
        pStmt.setObject(2, car.getStoreName(), Types.JAVA_OBJECT);
        pStmt.setObject(3, car.getLocation(), Types.JAVA_OBJECT);
        pStmt.setObject(4, car.getCarType(), Types.JAVA_OBJECT);
        pStmt.setString(5, "");
        pStmt.setTimestamp(6, null);
        pStmt.setTimestamp(7, null);
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