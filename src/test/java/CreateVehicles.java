import com.crd.carrental.factories.VehicleCreator;
import com.crd.carrental.factories.VehicleCreatorImpl;
import com.crd.carrental.rentalportfolio.components.RentalComponent;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**********************************************************************************************************************
 * Test covers NorthEastCarSupplier factory and Vehicle leaf nodes in the Composite.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CreateVehicles {
    VehicleCreator northEastCarSupplier = VehicleCreatorImpl.getInstance();

    @Test
    public void createCamry() {
        RentalComponent vehicle = northEastCarSupplier
                .createCar(Vehicles.CAMRY,  "47395726496058471", "1");

        String vehicleId = vehicle.getVehicleId();
        String storeId = vehicle.getStoreId();
        BigDecimal dailyPrice = vehicle.getDailyPrice();
        String classification = vehicle.getClassification();
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();
        int numberOfPassengers = vehicle.getNumberOfPassengers();
        long version = vehicle.getVersion();

        assertEquals("47395726496058471", vehicleId);
        assertEquals("1", storeId);
        assertEquals(new BigDecimal("75.00"), dailyPrice);
        assertEquals("Sedan", classification);
        assertEquals("Toyota", manufacturer);
        assertEquals("Camry", model);
        assertEquals(3, numberOfPassengers);
        assertEquals(0, version);
    }

    @Test
    public void createEscape() {
        RentalComponent vehicle = northEastCarSupplier
                .createCar(Vehicles.ESCAPE,  "47395726496058471", "1");

        String vehicleId = vehicle.getVehicleId();
        String storeId = vehicle.getStoreId();
        BigDecimal dailyPrice = vehicle.getDailyPrice();
        String classification = vehicle.getClassification();
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();
        int numberOfPassengers = vehicle.getNumberOfPassengers();
        long version = vehicle.getVersion();

        assertEquals("47395726496058471", vehicleId);
        assertEquals("1", storeId);
        assertEquals(new BigDecimal("125.00"), dailyPrice);
        assertEquals("SUV", classification);
        assertEquals("Ford", manufacturer);
        assertEquals("Escape", model);
        assertEquals(4, numberOfPassengers);
        assertEquals(0, version);
    }

    @Test
    public void createSienna() {
        RentalComponent vehicle = northEastCarSupplier
                .createCar(Vehicles.SIENNA,  "47395726496058471", "1");

        String vehicleId = vehicle.getVehicleId();
        String storeId = vehicle.getStoreId();
        BigDecimal dailyPrice = vehicle.getDailyPrice();
        String classification = vehicle.getClassification();
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();
        int numberOfPassengers = vehicle.getNumberOfPassengers();
        long version = vehicle.getVersion();

        assertEquals("47395726496058471", vehicleId);
        assertEquals("1", storeId);
        assertEquals(new BigDecimal("100.00"), dailyPrice);
        assertEquals("Van", classification);
        assertEquals("Toyota", manufacturer);
        assertEquals("Sienna", model);
        assertEquals(4, numberOfPassengers);
        assertEquals(0, version);
    }
}
