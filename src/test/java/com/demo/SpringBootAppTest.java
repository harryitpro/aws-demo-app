package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringBootAppTest {

    @Test
    void contextLoads(ApplicationContext context) {
        // Verify that the Spring application context loads successfully
        assertNotNull(context, "Application context should not be null");
    }

    @Test
    void mainMethodRunsWithoutException() {
        // Test that the main method executes without throwing exceptions
        String[] args = new String[]{};
        SpringBootApp.main(args);
        // No assertion needed; if an exception occurs, the test will fail
    }
}