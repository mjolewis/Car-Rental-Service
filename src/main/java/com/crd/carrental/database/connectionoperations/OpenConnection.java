package com.crd.carrental.database.connectionoperations;

import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**********************************************************************************************************************
 * Create a thread safe connection to a SQL database using the Singleton pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Configuration
@PropertySource("classpath:application.properties")
public class OpenConnection {
    private static volatile Connection con;
    private static final String DRIVER = "spring.datasource.driver-class-name";
    private static final String URL = "spring.datasource.url";
    private static final String USER_NAME = "spring.datasource.username";
    private static final String PASSWORD = "spring.datasource.password";
    @Autowired
    private Environment env;

    public OpenConnection() {}

    @Bean
    public Connection getDataSourceConnection() {
        if (con == null) {
            synchronized (OpenConnection.class) {
                if (con == null) {
                    try {
                        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                        dataSourceBuilder.driverClassName(env.getProperty(DRIVER));
                        dataSourceBuilder.url(env.getProperty(URL));
                        dataSourceBuilder.username(env.getProperty(USER_NAME));
                        dataSourceBuilder.password(env.getProperty(PASSWORD));
                        con = dataSourceBuilder.build().getConnection();
                    } catch (SQLException e) {
                        handleException(e);
                    }
                }
            }
        }

        return con;
    }

    private static void handleException(SQLException e) {
        e.printStackTrace();
    }
}
