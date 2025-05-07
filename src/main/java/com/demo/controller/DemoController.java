package com.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> greet() {
        String hostName = System.getenv().getOrDefault("HOSTNAME", "UNKNOWN");
        return ResponseEntity.ok(hostName + ": Welcome to Simple Demo Application!");
    }
}