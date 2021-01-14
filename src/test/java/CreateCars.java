import com.crd.carrental.factories.CarFactory;
import com.crd.carrental.factories.NorthEastCarSupplier;
import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.RentalComponent;
import com.crd.carrental.rentalportfolio.StoreLocations;
import com.crd.carrental.rentalportfolio.StoreNames;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.crd.carrental.rentalportfolio.CarTypes.*;
import static com.crd.carrental.rentalportfolio.CarTypes.Van;
import static com.crd.carrental.rentalportfolio.StoreLocations.*;
import static com.crd.carrental.rentalportfolio.StoreNames.*;

/**********************************************************************************************************************
 * Test covers NorthEastCarSupplier factory, Car instantiation, enums for Cars Locations and StoreNames,
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CreateCars {
    CarFactory northEastCarSupplier = new NorthEastCarSupplier();

    @Test
    public void createSedan() {
        RentalComponent car = northEastCarSupplier.createCar(Sedan,  "11111111111111111",
                StateStreetAlpha, Boston);

        CarTypes carType = car.getCarType();
        StoreLocations location = car.getLocation();
        StoreNames storeName = car.getStoreName();
        String vin = car.getVin();


        assertEquals(Sedan, carType, "Must be a Sedan");
        assertEquals(Boston, location, "Must be Boston");
        assertEquals(StateStreetAlpha, storeName, "Must be StatStreetAlpha");
        assertEquals("11111111111111111", vin, "Must be 11111111111111111");
    }

    @Test
    public void createSUV() {
        RentalComponent car = northEastCarSupplier.createCar(SUV, "44444444444444444",
                DataPlatform, Burlington);

        CarTypes carType = car.getCarType();
        StoreLocations location = car.getLocation();
        StoreNames storeName = car.getStoreName();
        String vin = car.getVin();


        assertEquals(SUV, carType, "Must be a SUV");
        assertEquals(Burlington, location, "Must be Burlington");
        assertEquals(DataPlatform, storeName, "Must be DataPlatform");
        assertEquals("44444444444444444", vin, "Must be 44444444444444444");
    }

    @Test
    public void createVan() {
        RentalComponent car = northEastCarSupplier.createCar(Van, "77777777777777777",
                StrategicCloud, Cambridge);

        CarTypes carType = car.getCarType();
        StoreLocations location = car.getLocation();
        StoreNames storeName = car.getStoreName();
        String vin = car.getVin();


        assertEquals(Van, carType, "Must be a Van");
        assertEquals(Cambridge, location, "Must be Cambridge");
        assertEquals(StrategicCloud, storeName, "Must be StrategicCloud");
        assertEquals("77777777777777777", vin, "Must be 77777777777777777");
    }
}
