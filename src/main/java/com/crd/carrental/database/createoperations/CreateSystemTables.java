package com.crd.carrental.database.createoperations;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**********************************************************************************************************************
 * Create a SQL table for our customers if it doesn't already exist.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CreateSystemTables extends CreateTableStrategy {
    private Connection con;
    private static final String CUSTOMER = "customer";
    private static final String STORE = "store";
    private static final String STORE_ID = "store_id";
    private static final String VEHICLE = "vehicle";
    private static final String RESERVATION = "reservation";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String VEHICLE_ID = "vehicle_id";

    public CreateSystemTables(Connection con) {
        this.con = con;
    }

    @Override
    public void createTable() {
        try {
            createCustomerTable();
            createStoreTable();
            createVehicleTable();
            createReservationTable();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void createCustomerTable() throws SQLException {
        Statement stmt = getStatementIfTableDoesntExist(con, CUSTOMER);

        if (stmt != null) {
            stmt.execute("CREATE TABLE " + CUSTOMER
                    + "(customer_id VARCHAR(100) PRIMARY KEY, "        // Email address
                    + "first_name VARCHAR(50) NOT NULL, "
                    + "last_name VARCHAR(50) NOT NULL, "
                    + "credit_card_number CHAR(19) NOT NULL);");
        }
    }

    private void createStoreTable() throws SQLException {
        Statement stmt = getStatementIfTableDoesntExist(con, STORE);

        if (stmt != null) {
            stmt.executeUpdate("CREATE TABLE " + STORE
                    + "(store_id VARCHAR(20) PRIMARY KEY, "
                    + "street_number VARCHAR(6) NOT NULL, "
                    + "street_name VARCHAR(20) NOT NULL, "
                    + "city VARCHAR(20) NOT NULL, "
                    + "state CHAR(2) NOT NULL, "
                    + "zip_code CHAR(5) NOT NULL);");
        }
    }

    private void createVehicleTable() throws SQLException {
        Statement stmt = getStatementIfTableDoesntExist(con, VEHICLE);

        if (stmt != null) {
            stmt.executeUpdate("CREATE TABLE " + VEHICLE
                    + "(vehicle_id CHAR(17) PRIMARY KEY, "
                    + "store_id VARCHAR(20) NOT NULL REFERENCES " + STORE + "(" + STORE_ID + "), "
                    + "daily_price DECIMAL(5 , 2 ) NOT NULL, "
                    + "classification VARCHAR(20) NOT NULL, "
                    + "manufacturer VARCHAR(20) NOT NULL, "
                    + "model VARCHAR(20) NOT NULL, "
                    + "number_of_passengers INT NOT NULL, "
                    + "version INT NOT NULL);");
        }
    }

    private void createReservationTable() throws SQLException {
        Statement stmt = getStatementIfTableDoesntExist(con, RESERVATION);

        if (stmt != null) {
            stmt.executeUpdate("CREATE TABLE " + RESERVATION
                    + "(reservation_id VARCHAR(20) PRIMARY KEY, "
                    + "customer_id VARCHAR(100) NOT NULL REFERENCES " + CUSTOMER + "(" + CUSTOMER_ID + "), "
                    + "vehicle_id CHAR(17) NOT NULL REFERENCES " + VEHICLE + "(" + VEHICLE_ID + "), "
                    + "start TIMESTAMP NOT NULL, "
                    + "end TIMESTAMP NOT NULL);");
        }
    }
}