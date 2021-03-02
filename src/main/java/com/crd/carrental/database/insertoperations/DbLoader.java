package com.crd.carrental.database.insertoperations;

import com.crd.carrental.database.connectionoperations.CloseConnection;
import com.crd.carrental.database.connectionoperations.OpenConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**********************************************************************************************************************
 * A collection of JDBC helper methods to insert initial records into database tables. Utility classes should not have
 * a public or default constructor.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class DbLoader {

    public static void insertStoreRecords() {
        String dmlCommand = "INSERT IGNORE INTO "
                + "store(store_id, street_number, street_name, city, state, zip_code) VALUES"
                + "(1, '123', 'Ada Lovelace Lane', 'Boston', 'MA', '02115'), "
                + "(2, '456', 'Alan Turing Drive', 'Cambridge', 'MA', '02210'), "
                + "(3, '123', 'Von Neumann Blvd', 'Burlington', 'MA', '02349');";

        executeUpdate(dmlCommand);
    }

    public static void insertVehicleRecords() {
        String dmlCommand = "INSERT IGNORE INTO "
                + "vehicle(vehicle_id, store_id, daily_price, classification, make, model, number_of_passengers) VALUES"
                + "('d8bUie2IPBnsJ6Tm8', 1, 75.00, 'Sedan', 'Toyota', 'Camry', 3), "
                + "('Uy9pA01zCKIsGCgiN', 1, 125.00, 'SUV', 'Ford', 'Escape', 1), "
                + "('sv1BMLg2mzs29juWX', 1, 100.00, 'Van', 'Ford', 'Sienna', 4), "
                + "('zESmC4Ux31cb7ExTs', 2, 75.00, 'Sedan', 'Toyota', 'Camry', 3), "
                + "('CBgQOOEvzcWB9jlYQ', 2, 125.00, 'SUV', 'Ford', 'Escape', 1), "
                + "('cFhPV601NuiFf2HyJ', 2, 100.00, 'Van', 'Ford', 'Sienna', 4), "
                + "('jnUM0PDy83wWEzjz3', 3, 75.00, 'Sedan', 'Toyota', 'Camry', 3), "
                + "('Yy2CIT3eUuX9I4XZV', 3, 125.00, 'SUV', 'Ford', 'Escape', 1), "
                + "('RigIwIQgOWZVXI6Cv', 3, 100.00, 'Van', 'Ford', 'Sienna', 4);";

        executeUpdate(dmlCommand);
    }

    public static void insertCustomerRecords() {
        String dmlCommand = "INSERT IGNORE INTO "
                + "customer(customer_id, first_name, last_name, credit_card_number) VALUES"
                + "('mjolewis@gmail.com', 'Michael', Lewis', '94383422234098234');" ;

        executeUpdate(dmlCommand);
    }

    public static void insertReservationRecords() {
        String dmlCommand = "INSERT IGNORE INTO "
                + "reservation(reservation_id, customer_id, vehicle_id, start, end) VALUES"
                + "('7ZoRqJ5IcpZHMJgNo0Fm', 'mjolewis@gmail.com', 'sv1BMLg2mzs29juWX', '2021-03-01 12:00:00', '2021-03-10 14:00:00'), "
                + "('JC92qJ5IcpZHMJgNo0Fm', 'mjolewis@bu.edu', 'sv1BMLg2mzs29juWX', '2021-03-11 12:00:00', '2021-03-15 14:00:00');";

        executeUpdate(dmlCommand);
    }

    /**
     * Assign the confirmed reservation number to a car that the customer requested. This allows an employee to
     * match a customers reservation number to a particular car when the customer shows up at the store.
     */
    private static void executeUpdate(String DmlCommand) {
        Connection con = OpenConnection.getInstance();
        PreparedStatement pStmt = null;
        try {
            pStmt = con.prepareStatement(DmlCommand);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection.closeQuietly(pStmt);
        }
    }
}
