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
public class Insert implements InsertStrategy {
    private Connection con;
    private String tableName;

    public Insert(Connection con, String tableName) {
        this.con = con;
        this.tableName = tableName;
    }

    @Override
    public void insert(RentalComponent cars) {
        Iterator<RentalComponent> iterator = cars.createIterator();

        while (iterator.hasNext()) {
            RentalComponent car = iterator.next();
            try {
                insertCarIfRecordDoesntExist(car);
            } catch (SQLException e) {
                handleException(e);
            }
        }
    }

    private void insertCarIfRecordDoesntExist(RentalComponent car) throws SQLException {

        String sqlInsert = "INSERT IGNORE INTO " + tableName + " values(?, ?, ?, ?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        PreparedStatement pStmt = con.prepareStatement(sqlInsert);
        pStmt.setString(1, car.getVin());
        pStmt.setString(2, car.getStoreName());
        pStmt.setString(3, car.getLocation());
        pStmt.setObject(4, car.getCarType(), Types.JAVA_OBJECT);
        pStmt.setBoolean(5, car.isReserved());
        pStmt.setBoolean(6, car.isAvailable());
        pStmt.setTimestamp(7, car.getReservationStartDateAndTime());
        pStmt.setTimestamp(8, car.getReservationEndDateAndTime());
        pStmt.executeUpdate();

        closePreparedStatement(pStmt);
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }

    private void closePreparedStatement(PreparedStatement pStmt) {
        ConnectionCloser.closeQuietly(pStmt);
    }
}
