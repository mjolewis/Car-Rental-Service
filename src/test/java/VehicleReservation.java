import com.crd.carrental.controllers.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**********************************************************************************************************************
 * Test the ExistingReservation data flow from Controller to Model classes.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class VehicleReservation {

    @Test
    public void testNewReservation() {
        String start = createDateTimeLocal(System.currentTimeMillis() + 100000);
        String end = createDateTimeLocal(System.currentTimeMillis() + 100000);

        NewReservationRequest reservationRequest =
                new NewReservationRequest("Cambridge", "Sedan", start, end, "Michael",
                        "Lewis", "mjolewis@bu.edu", "2837-9237-4293-7423");
        NewReservationController controller = new NewReservationController();
        DataTransferObject dto = controller.requestReservation(reservationRequest);

        assertEquals("456", dto.getStreetNumber());
        assertEquals("Alan Turing Drive", dto.getStreetName());
        assertEquals("Cambridge", dto.getCity());
        assertEquals("MA", dto.getState());
        assertEquals("02210", dto.getZipCode());
        assertTrue(dto.isAvailable());
    }

    private String createDateTimeLocal(long currentTimeMillis) {
        Date startDate = new Date(currentTimeMillis);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return df.format(startDate);
    }

    @Test
    public void testExistingReservation() {
        String reservationId = "LZI9FPURU491IMIIZG15";
        ExistingReservationRequest reservationRequest = new ExistingReservationRequest(reservationId);
        ExistingReservationController controller = new ExistingReservationController();
        DataTransferObject dto = controller.lookupReservationId(reservationRequest);

        assertEquals("Michael", dto.getFirstName());
        assertEquals("Lewis", dto.getLastName());
        assertEquals("2021-03-05 10:50:00.0", dto.getStart().toString());
        assertEquals("2021-03-05 11:05:00.0", dto.getEnd().toString());
        assertEquals("Toyota", dto.getManufacturer());
        assertEquals("Camry", dto.getModel());
        assertEquals(new BigDecimal("75.00"), dto.getDailyPrice());
        assertEquals("456", dto.getStreetNumber());
        assertEquals("Alan Turing Drive", dto.getStreetName());
        assertEquals("Cambridge", dto.getCity());
        assertEquals("MA", dto.getState());
        assertEquals("02210", dto.getZipCode());
    }
}
