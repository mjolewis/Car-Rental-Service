import com.crd.carrental.controllers.*;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import org.junit.Test;
import java.math.BigDecimal;
import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**********************************************************************************************************************
 * Test the ExistingReservation data flow from Controller to Model classes.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class VehicleReservation {
    private static final String CITY = "Cambridge";
    private static final String CLASSIFICATION = Vehicles.CAMRY.getClassification();
    private static final String FIRST_NAME = "Michael";
    private static final String LAST_NAME = "Lewis";
    private static final String START = new Timestamp(System.currentTimeMillis()).toString();
    private static final String END = new Timestamp(System.currentTimeMillis()).toString();
    private static final String CUSTOMER_ID = "mjolewis@bu.edu";
    private static final String CREDIT_CARD_NUMBER = "2837-9237-4293-7423";
    private static final String MANUFACTURER = Vehicles.CAMRY.getManufacturer();
    private static final String MODEL = Vehicles.CAMRY.getModel();
    private static final BigDecimal DAILY_PRICE = Vehicles.CAMRY.getDailyPrice();
    private static final String STREET_NUMBER = "456";
    private static final String STREET_NAME = "Alan Turing Drive";
    private static final String STATE = "MA";
    private static final String ZIP_CODE = "02210";

    @Test
    public void testNewReservation() {
        NewReservationRequest reservationRequest = new NewReservationRequest(CITY, CLASSIFICATION, START, END,
                FIRST_NAME, LAST_NAME, CUSTOMER_ID, CREDIT_CARD_NUMBER);
        NewReservationController controller = new NewReservationController();
        DataTransferObject dto = controller.requestReservation(reservationRequest);

        assertEquals(FIRST_NAME, dto.getReservationId());
        assertEquals(FIRST_NAME, dto.getVehicleId());
        assertEquals(STREET_NUMBER, dto.getStreetNumber());
        assertEquals(STREET_NAME, dto.getStreetName());
        assertEquals(CITY, dto.getCity());
        assertEquals(STATE, dto.getState());
        assertEquals(ZIP_CODE, dto.getZipCode());
        assertTrue(dto.isAvailable());
    }

    @Test
    public void testExistingReservation() {
        ExistingReservationRequest reservationRequest =
                new ExistingReservationRequest("2AI2Z2OME4J0CBVT59WE");
        ExistingReservationController controller = new ExistingReservationController();
        DataTransferObject dto = controller.lookupReservationId(reservationRequest);

        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
        assertEquals(START, dto.getStart().toString());
        assertEquals(END, dto.getEnd().toString());
        assertEquals(MANUFACTURER, dto.getManufacturer());
        assertEquals(MODEL, dto.getModel());
        assertEquals(DAILY_PRICE, dto.getDailyPrice());
        assertEquals(STREET_NUMBER, dto.getStreetNumber());
        assertEquals(STREET_NAME, dto.getStreetName());
        assertEquals(CITY, dto.getCity());
        assertEquals(START, dto.getState());
        assertEquals(ZIP_CODE, dto.getZipCode());
    }
}
