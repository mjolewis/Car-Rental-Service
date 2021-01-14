import com.crd.carrental.database.*;
import com.crd.carrental.factories.CarFactory;
import com.crd.carrental.factories.NorthEastCarSupplier;
import com.crd.carrental.rentalportfolio.*;
import org.junit.Test;

import java.sql.*;

import static com.crd.carrental.rentalportfolio.CarTypes.Sedan;
import static com.crd.carrental.rentalportfolio.StoreLocations.Boston;
import static com.crd.carrental.rentalportfolio.StoreNames.StateStreetAlpha;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test inserting a car into a database and selecting the same car from the database.
 */
public class InsertAndSelect {
    CarFactory northEastCarSupplier = new NorthEastCarSupplier();
    RentalComponent car;
    private Connection con = CreateConnection.getInstance();
    private PreparedStatement pStmt;
    private ResultSet resultSet;
    private RentalComponent stateStreetAlpha;
    private String vin;
    private CarTypes carType;
    private StoreLocations location;
    private StoreNames storeName;
    private boolean isReserved;
    private boolean isAvailable;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;
    private Timestamp startDate;
    private Timestamp endDate;

    @Test
    public void isCarInDatabase() {
        createCarAndStore();
        insertCar();
        selectCar();
    }

    private void createCarAndStore() {
        car = northEastCarSupplier.createCar(Sedan,  "11111111111111111", StateStreetAlpha, Boston);

        stateStreetAlpha = new RentalStore(StateStreetAlpha);
        stateStreetAlpha.add(car);
    }

    private void insertCar() {

        Connection con = CreateConnection.getInstance();

        CreateTableStrategy inventoryTable = new CreateInventoryTable(con);
        inventoryTable.createTable("cars");

        InsertStrategy insertStrategy = new InsertInventory(con, "cars");
        insertStrategy.insert(stateStreetAlpha);
    }

    private void selectCar() {
        String selectStatement = "SELECT * FROM cars " +
                "WHERE location LIKE ? " +
                "AND carType = ? " +
                "AND isAvailable = ? " +
                "AND (reservationStartDateAndTime > ? OR reservationEndDateAndTime < ? " +
                "OR (reservationStartDateAndTime IS NULL AND reservationEndDateAndTime IS NULL))" +
                "LIMIT 1";

        try {
            createPreparedStatement(selectStatement);
            executeQuery();
            getCarInfoFromResultSet();
        } catch (
                SQLException e) {
            handleException(e);
        }

        CloseConnection.closeQuietly(resultSet, pStmt);
    }

    private void createPreparedStatement(String selectStatement) throws SQLException {
        startDate = Timestamp.valueOf("2021-02-02 12:00:00");
        endDate = Timestamp.valueOf("2021-02-05 12:00:00");

        pStmt = con.prepareStatement(selectStatement);
        pStmt.setObject(1, car.getLocation(), Types.JAVA_OBJECT);
        pStmt.setObject(2, car.getCarType(), Types.JAVA_OBJECT);
        pStmt.setBoolean(3, true);
        pStmt.setTimestamp(4, startDate);
        pStmt.setTimestamp(5, endDate);

        runTest();
    }

    private ResultSet executeQuery() throws SQLException {
        return pStmt.executeQuery();
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }

    private void getCarInfoFromResultSet() throws SQLException {

        if (resultSet.next()) {
            vin = resultSet.getString("vin");
            location = (StoreLocations) resultSet.getObject("location");
            carType = (CarTypes) resultSet.getObject("carType");
            storeName = (StoreNames) resultSet.getObject("storeName");
            isReserved = resultSet.getBoolean("isReserved");
            isAvailable = resultSet.getBoolean("isAvailable");
            reservationStartDateAndTime = resultSet.getTimestamp("reservationStartDateAndTime");
            reservationEndDateAndTime = resultSet.getTimestamp("reservationEndDateAndTime");
        }
    }

    private void runTest() {
        //assertEquals("11111111111111111", vin, "Must be 11111111111111111");
        assertEquals(Sedan, carType, "Must be a Sedan");
        assertEquals(Boston, location, "Must be Boston");
        assertEquals(StateStreetAlpha, storeName, "Must be StatStreetAlpha");
        assertFalse(isReserved, "Must be false");
        assertTrue(isAvailable, "Must be true");
        assertEquals(startDate, reservationStartDateAndTime, "Must be 2021-02-02 12:00:00");
        assertEquals(endDate, reservationEndDateAndTime, "Must be 2021-02-05 12:00:00");
    }
}