package com.crd.carrental.database;

import java.sql.*;

/**********************************************************************************************************************
 * Declares an interface common to all supported database table creation algorithms. Concrete strategies must implement
 * this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class CreateTableStrategy {

    public abstract void createTable(String tableName);

    public Statement getStatementIfTableDoesntExist(Connection con, String tableName) throws SQLException {
        Statement stmt = createStatement(con);
        DatabaseMetaData metaData = getConnectionMetaData(con);
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);

        if (!doesTableExist(resultSet)) {
            return stmt;
        }
        return null;
    }

    public Statement createStatement(Connection con) throws SQLException {
        return con.createStatement();
    }

    public DatabaseMetaData getConnectionMetaData(Connection con) throws SQLException {
        return con.getMetaData();
    }

    public boolean doesTableExist(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }
}
