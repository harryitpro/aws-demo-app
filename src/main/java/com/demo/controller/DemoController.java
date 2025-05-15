package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping
    public ResponseEntity<String> greet() {
        logger.debug("Received GET request to /demo endpoint");
        String hostName = System.getenv().getOrDefault("HOSTNAME", "UNKNOWN");
        logger.debug("Retrieved hostname: {}", hostName);
        String response = hostName + ": Welcome to Simple Demo Application!";
        logger.info("Responding to /demo with: {}", response);
        return ResponseEntity.ok(response);
    }
}