package com.crd.carrental.database.insertoperations;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.database.connectionoperations.CloseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Declares an interface common to all supported database insert algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class InsertStrategy {

    public abstract void insert(ReservationController controller);

    public void executeUpdate(PreparedStatement pStmt) throws SQLException {
        pStmt.executeUpdate();
    }

    public void handleException(SQLException e) {
        e.printStackTrace();
    }

    public void closePreparedStatement(PreparedStatement pStmt) {
        CloseConnection.closeQuietly(pStmt);
    }
}