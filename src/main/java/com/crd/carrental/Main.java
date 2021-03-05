package com.crd.carrental;

import com.crd.carrental.database.connectionoperations.OpenConnection;
import com.crd.carrental.database.createoperations.CreateSystemTables;
import com.crd.carrental.database.createoperations.CreateTableStrategy;
import com.crd.carrental.database.insertoperations.DbLoader;
import com.crd.carrental.database.insertoperations.InsertVehicles;
import com.crd.carrental.factories.VehicleFactory;
import com.crd.carrental.factories.VehicleFactoryImpl;
import com.crd.carrental.rentalportfolio.components.RentalComponent;
import com.crd.carrental.rentalportfolio.components.RentalStore;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import com.crd.carrental.utils.Hasher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

/**********************************************************************************************************************
 * Main entry point. After the initial setup, a customer can make a reservation by going http://localhost:8080/?.
 * Please note that in this demonstration, the store_id is fixed when adding new cars; however, the system can be
 * extended to add additional stores.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        //private static Logger logger = Logger.get

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(Main.class);
        springBuilder.headless(false);
        springBuilder.run(args);

        // Create the database tables if they don't already exist
        if (createOptionPaneForDatabaseTableSelection() == 0) {
            createTables();
        }

        // Add initial records to tables
        if (createOptionPaneForSeedingDatabase() == 0) {
            seedDatabase();
        }

        // Each vehicle is owned by a store. So first create the store and then add vehicles to the store. Vehicles
        // are created by the VehicleFactoryImpl and then added to the vehicle table..
        RentalComponent store = new RentalStore("1");

        // Create dialog box to create more Sedans
        int numberOfSedansToAdd = createOptionPaneForAddingAdditionalSedan();
        for (int i = 0; i < numberOfSedansToAdd; ++i) {
            VehicleFactory factory = new VehicleFactoryImpl();
            store.add(
                    factory.createCar(
                            Vehicles.CAMRY,
                            Hasher.getSeventeenCharacterSaltString(),
                            store.getStoreId()));
        }

        // Create dialog box to create more SUVs
        int numberOfSuvsToAdd = createOptionPaneForAddingAdditionalSuv();
        for (int i = 0; i < numberOfSuvsToAdd; ++i) {
            VehicleFactory factory = new VehicleFactoryImpl();
            store.add(
                    factory.createCar(
                            Vehicles.ESCAPE,
                            Hasher.getSeventeenCharacterSaltString(),
                            store.getStoreId()));
        }

        // Create dialog box to create more Vans
        int numberOfVansToAdd = createOptionPaneForAddingAdditionalVan();
        for (int i = 0; i < numberOfVansToAdd; ++i) {
            VehicleFactory factory = new VehicleFactoryImpl();
            store.add(
                    factory.createCar(Vehicles.SIENNA,
                            Hasher.getSeventeenCharacterSaltString(),
                            store.getStoreId()));
        }

        // Add all vehicles to the database
        InsertVehicles vehicleTable = new InsertVehicles();
        vehicleTable.insert(store);
    }

    private static int createOptionPaneForDatabaseTableSelection() {
        final JFrame frame = new JFrame();
        String[] options = {"Yes", "No"};

        return JOptionPane.showOptionDialog(
                frame,
                "Create database tables?",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private static void createTables() {
        CreateTableStrategy create = new CreateSystemTables(OpenConnection.getDataSourceConnection());
        create.createTable();
    }

    private static int createOptionPaneForSeedingDatabase() {
        final JFrame frame = new JFrame();
        String[] options = {"Yes", "No"};

        return JOptionPane.showOptionDialog(
                frame,
                "Seed the database?",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private static void seedDatabase() {
        DbLoader.insertStoreRecords();
        DbLoader.insertVehicleRecords();
        DbLoader.insertCustomerRecords();
        DbLoader.insertReservationRecords();
    }

    private static int createOptionPaneForAddingAdditionalSedan() {
        final JFrame frame = new JFrame();
        Integer[] options = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        return (int) JOptionPane.showInputDialog(
                frame,
                "How many Sedans do you want to add to the database?",
                "Sedan Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
    }

    private static int createOptionPaneForAddingAdditionalSuv() {
        final JFrame frame = new JFrame();
        Integer[] options = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        return (int) JOptionPane.showInputDialog(
                frame,
                "How many SUVs do you want to add to the database?",
                "Sedan Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
    }

    private static int createOptionPaneForAddingAdditionalVan() {
        final JFrame frame = new JFrame();
        Integer[] options = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        return (int) JOptionPane.showInputDialog(
                frame,
                "How many Vans do you want to add to the database?",
                "Sedan Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
    }
}
