package com.crd.carrental.database.connectionoperations;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**********************************************************************************************************************
 * Create a thread safe connection to a SQL database using the Singleton pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class OpenConnection {
    private static volatile Connection con;
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PATH = "src/main/resources/secrets.JSON";

    private OpenConnection() {}

    public static Connection getDataSourceConnection() {
        if (con == null) {
            synchronized (OpenConnection.class) {
                if (con == null) {
                    try (BufferedReader reader = new BufferedReader(
                            Files.newBufferedReader(Paths.get(PATH), StandardCharsets.UTF_8))) {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
                        String url = (String) jsonObject.get(URL);
                        String username = (String) jsonObject.get(USERNAME);
                        String password = (String) jsonObject.get(PASSWORD);
                        con = DriverManager.getConnection(url, username, password);
                    } catch (IOException | ParseException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return con;
    }
}
