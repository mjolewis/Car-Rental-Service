package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Declares an interface common to all supported database update operations algorithms. Concrete strategies must
 * implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class UpdateStrategy {

    public abstract void update(ReservationController controller);


    public void executeUpdate(PreparedStatement pStmt) throws SQLException {
        pStmt.executeUpdate();                                  // Don't need return value so throw it away
    }

    public void handleException(SQLException e) {
        e.printStackTrace();
    }

    public void closePreparedStatement(PreparedStatement pStmt) {
        CloseConnection.closeQuietly(pStmt);
    }
}
