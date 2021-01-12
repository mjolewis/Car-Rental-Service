package com.crd.carrental;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**********************************************************************************************************************
 * Main entry point
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootApplication
public class CarRentalApplication {

    public static void main(String[] args) {

        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder(CarRentalApplication.class);
        springBuilder.headless(false);
        ConfigurableApplicationContext context = springBuilder.run(args);
    }
}
