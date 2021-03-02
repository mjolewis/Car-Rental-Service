package com.crd.carrental.database.selectoperations;

import com.crd.carrental.controllers.ExistingReservationController;
import com.crd.carrental.controllers.NewReservationController;
import com.crd.carrental.controllers.Response;
import com.crd.carrental.database.connectionoperations.CloseConnection;
import java.sql.*;

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
    PreparedStatement pStmt;
    ResultSet resultSet;

    public Response select(NewReservationController controller) {
        throw new UnsupportedOperationException();
    }

    public Response select(ExistingReservationController controller) {
        throw new UnsupportedOperationException();
    }

    public abstract void createPreparedStatement(String selectStatement) throws SQLException;

    public ResultSet executeQuery(PreparedStatement pStmt) throws SQLException {
        return pStmt.executeQuery();
    }

    public Response isRecordFound() throws SQLException {
        if (resultSet.next()) {
            return createValidResponse(resultSet);
        }

        return createInvalidResponse();
    }

    public abstract Response createValidResponse(ResultSet resultSet) throws SQLException;

    public abstract Response createInvalidResponse();

    public void handleException(SQLException e) {
        e.printStackTrace();
    }

    public void closePreparedStatement(ResultSet resultSet, PreparedStatement pStmt) {
        CloseConnection.closeQuietly(resultSet, pStmt);
    }
}
