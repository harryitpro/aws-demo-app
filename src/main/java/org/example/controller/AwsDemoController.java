package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class AwsDemoController {

    @GetMapping
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Welcome to AWS Demo Application!");
    }
}
