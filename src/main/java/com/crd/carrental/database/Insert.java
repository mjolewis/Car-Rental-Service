package com.crd.carrental.database;

import com.crd.carrental.rentalportfolio.RentalComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**********************************************************************************************************************
 *
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Insert {
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

        String sqlInsert = "INSERT IGNORE INTO " + tableName + " values(?, ?, ?, ?, ?)";

        // Prepared Statements prevent SQL injection and efficiently execute the statement multiple times
        PreparedStatement pStmt = con.prepareStatement(sqlInsert);
        pStmt.setString(1, car.getVin());
        pStmt.setString(2, car.getLocation());
        pStmt.setString(3, car.getCarType());
        pStmt.setBoolean(4, car.isRented());
        pStmt.setDate(5, car.getReservationStartDate());
        pStmt.setDate(6, car.getReservationEndDate());
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
