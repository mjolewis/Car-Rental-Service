package com.crd.carrental;

import com.crd.carrental.database.*;
import com.crd.carrental.factories.*;
import com.crd.carrental.rentalportfolio.RentalComponent;
import com.crd.carrental.rentalportfolio.RentalStore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Connection;

import static com.crd.carrental.rentalportfolio.CarTypes.*;
import static com.crd.carrental.rentalportfolio.StoreNames.*;
import static com.crd.carrental.rentalportfolio.StoreLocations.*;

/**********************************************************************************************************************
 * Main entry point. After the initial setup, a customer can make a reservation by going http://localhost:8080/?
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootApplication
public class CarRentalApplication {

    public static void main(String[] args) {

        CarFactory northEastCarSupplier = new NorthEastCarSupplier();

        // Use the CarFactory to get our initial inventory of cars
        RentalComponent whiteSedan = northEastCarSupplier.createCar(Sedan,  "11111111111111111",
                StateStreetAlpha, Boston);
        RentalComponent blackSedan = northEastCarSupplier.createCar(Sedan, "22222222222222222",
                DataPlatform, Burlington);
        RentalComponent greySedan = northEastCarSupplier.createCar(Sedan, "33333333333333333",
                StrategicCloud, Cambridge);

        RentalComponent whiteSUV = northEastCarSupplier.createCar(SUV, "44444444444444444",
                StateStreetAlpha, Boston);
        RentalComponent blackSUV = northEastCarSupplier.createCar(SUV, "55555555555555555",
                DataPlatform, Burlington);
        RentalComponent greySUV = northEastCarSupplier.createCar(SUV, "66666666666666666",
                StrategicCloud, Cambridge);

        RentalComponent whiteVan = northEastCarSupplier.createCar(Van, "77777777777777777",
                StateStreetAlpha, Boston);
        RentalComponent blackVan = northEastCarSupplier.createCar(Van, "88888888888888888",
                DataPlatform, Burlington);
        RentalComponent greyVan = northEastCarSupplier.createCar(Van, "99999999999999999",
                StrategicCloud, Cambridge);

        // Create a portfolio of stores
        RentalComponent parentCompany = new RentalStore(ParentCompany);
        RentalComponent stateStreetAlpha = new RentalStore(StateStreetAlpha);
        RentalComponent dataPlatform = new RentalStore(DataPlatform);
        RentalComponent strategicCloud = new RentalStore(StrategicCloud);

        // Add cars to the appropriate stores
        stateStreetAlpha.add(whiteSedan);
        stateStreetAlpha.add(whiteSUV);
        stateStreetAlpha.add(whiteVan);

        dataPlatform.add(blackSedan);
        dataPlatform.add(blackSUV);
        dataPlatform.add(blackVan);

        strategicCloud.add(greySUV);
        strategicCloud.add(greySedan);
        strategicCloud.add(greyVan);

        // Add the stores to the parent company portfolio
        parentCompany.add(stateStreetAlpha);
        parentCompany.add(dataPlatform);
        parentCompany.add(strategicCloud);

        Connection con = CreateConnection.getInstance();

        CreateTableStrategy inventoryTable = new CreateInventoryTable(con);
        inventoryTable.createTable("cars");

        InsertNewInventory insertNewInventory = new InsertNewInventory(con, "cars");
        insertNewInventory.insert(parentCompany);

        CreateTableStrategy customerTable = new CreateCustomerTable(con);
        customerTable.createTable("customers");

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(CarRentalApplication.class);
        springBuilder.headless(false);
        ConfigurableApplicationContext context = springBuilder.run(args);
    }
}
