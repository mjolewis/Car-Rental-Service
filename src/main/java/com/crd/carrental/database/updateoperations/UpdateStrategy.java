package com.crd.carrental.database.updateoperations;

import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.database.connectionoperations.CloseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Declares an interface common to all supported database update operations algorithms. Concrete strategies must
 * implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class UpdateStrategy {

    public abstract void update(NewReservationController controller);


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
