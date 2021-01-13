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
                "alphaRentACar","Boston");
        RentalComponent blackSedan = northEastCarSupplier.createCar(Sedan, "22222222222222222",
                "alphaRentACar", "Cambridge");
        RentalComponent greySedan = northEastCarSupplier.createCar(Sedan, "33333333333333333",
                "alphaRentACar", "Burlington");

        RentalComponent whiteSUV = northEastCarSupplier.createCar(SUV, "44444444444444444",
                "alphaRentACar", "Boston");
        RentalComponent blackSUV = northEastCarSupplier.createCar(SUV, "55555555555555555",
                "alphaRentACar", "Cambridge");
        RentalComponent greySUV = northEastCarSupplier.createCar(SUV, "66666666666666666",
                "alphaRentACar", "Burlington");

        RentalComponent whiteVan = northEastCarSupplier.createCar(Van, "77777777777777777",
                "alphaRentACar", "Boston");
        RentalComponent blackVan = northEastCarSupplier.createCar(Van, "88888888888888888",
                "alphaRentACar", "Cambridge");
        RentalComponent greyVan = northEastCarSupplier.createCar(Van, "99999999999999999",
                "alphaRentACar", "Burlington");

        // Create a portfolio of stores
        RentalComponent parentCompany = new RentalStore("parentCompany");
        RentalComponent alphaRentACar = new RentalStore("alphaRentACar");

        // AlphaRentACar is one store in our portfolio
        alphaRentACar.add(whiteSedan);
        alphaRentACar.add(blackSedan);
        alphaRentACar.add(greySedan);
        alphaRentACar.add(whiteSUV);
        alphaRentACar.add(blackSUV);
        alphaRentACar.add(greySUV);
        alphaRentACar.add(whiteVan);
        alphaRentACar.add(blackVan);
        alphaRentACar.add(greyVan);

        parentCompany.add(alphaRentACar);                       // Add the store to the parent company portfolio

        // Create the connection, database, table, and insert the cars
        Connection con = ConnectionCreator.getInstance();
        TableStrategy tableCreator = new TableCreator(con);
        tableCreator.createTableIfDoesntExist("cars");

        InsertStrategy insertStrategy = new Insert(con, "cars");
        insertStrategy.insert(alphaRentACar);

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(CarRentalApplication.class);
        springBuilder.headless(false);
        ConfigurableApplicationContext context = springBuilder.run(args);
    }
}
