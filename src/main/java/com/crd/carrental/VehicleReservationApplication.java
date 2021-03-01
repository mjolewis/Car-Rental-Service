package com.crd.carrental;

import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.database.createoperations.CreateSystemTables;
import com.crd.carrental.database.createoperations.CreateTableStrategy;
import com.crd.carrental.database.insertoperations.InsertSeedData;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

/**********************************************************************************************************************
 * Main entry point. After the initial setup, a customer can make a reservation by going http://localhost:8080/?
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootApplication
public class VehicleReservationApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Create database tables (y/n)? ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            createTables();
        }

        System.out.print("Seed the database (y/n)? ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            seedDatabase();
        }

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(VehicleReservationApplication.class);
        springBuilder.headless(false);
        ConfigurableApplicationContext context = springBuilder.run(args);
    }

    private static void createTables() {
        CreateTableStrategy create = new CreateSystemTables(OpenConnection.getInstance());
        create.createTable();
    }

    private static void seedDatabase() {
        InsertSeedData.insertStoreRecords();
        InsertSeedData.insertVehicleRecords();
        InsertSeedData.insertCustomerRecords();
        InsertSeedData.insertReservationRecords();
    }
}
