package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootApp {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootApp.class);

    public static void main(String[] args) {
        logger.info("Starting Spring Boot application");
        try {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootApp.class, args);
            logger.info("SpringBootApp started successfully");
        } catch (Exception ex) {
            logger.error("Failed to start Spring Boot application", ex);
            throw ex; // Re-throw to maintain default behavior
        }
    }
}
