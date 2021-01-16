package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.controllers.ReservationResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************************************************************************************************
 * Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class SelectStrategy {

    public abstract ReservationResponse select(ReservationController controller);

    public ResultSet executeQuery(PreparedStatement pStmt) throws SQLException {
        return pStmt.executeQuery();
    }

    public void handleException(SQLException e) {
        e.printStackTrace();
    }

    public void closePreparedStatement(ResultSet resultSet, PreparedStatement pStmt) {
        CloseConnection.closeQuietly(resultSet, pStmt);
    }
}
