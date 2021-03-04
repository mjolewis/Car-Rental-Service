package com.crd.carrental.database.selectoperations;

import com.crd.carrental.controllers.DataTransferObject;
import com.crd.carrental.controllers.ExistingReservationController;
import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.database.connectionoperations.CloseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**********************************************************************************************************************
 * Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class SelectStrategy {
    Connection con;
    String city;
    String classification;
    Timestamp start;
    Timestamp end;
    String reservationId;
    PreparedStatement pstmt;
    ResultSet resultSet;

    public DataTransferObject select(NewReservationController controller) {
        throw new UnsupportedOperationException();
    }

    public DataTransferObject select(ExistingReservationController controller) {
        throw new UnsupportedOperationException();
    }

    public abstract void createPreparedStatement(String selectStatement) throws SQLException;

    public ResultSet executeQuery(PreparedStatement pstmt) throws SQLException {
        return pstmt.executeQuery();
    }

    public abstract DataTransferObject createValidResponse(ResultSet resultSet) throws SQLException;

    public abstract DataTransferObject createInvalidResponse();

    public void handleException(SQLException e) {
        e.printStackTrace();
    }

    public void closePreparedStatement(ResultSet resultSet, PreparedStatement pstmt) {
        CloseConnection.closeQuietly(resultSet, pstmt);
    }
}
