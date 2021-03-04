package com.crd.carrental;

import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.database.createoperations.CreateSystemTables;
import com.crd.carrental.database.createoperations.CreateTableStrategy;
import com.crd.carrental.database.insertoperations.DbLoader;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**********************************************************************************************************************
 * Main entry point. After the initial setup, a customer can make a reservation by going http://localhost:8080/?
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, "UTF-8");
        System.out.print("Create database tables (y/n)? ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            createTables();
        }

        System.out.print("Seed the database (y/n)? ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            seedDatabase();
        }

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(Main.class);
        springBuilder.headless(false);
        springBuilder.run(args);
    }

    private static void createTables() {
        CreateTableStrategy create = new CreateSystemTables(OpenConnection.getDataSourceConnection());
        create.createTable();
    }

    private static void seedDatabase() {
        DbLoader.insertStoreRecords();
        DbLoader.insertVehicleRecords();
        DbLoader.insertCustomerRecords();
        DbLoader.insertReservationRecords();
    }
}
