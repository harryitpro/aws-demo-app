package com.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(basePackages = "com.demo.controller")
public class GlobalControllerAdvice {

    // Global exception handling

    /**
     * Handles NullPointerException globally for controllers in com.example.controllers.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException ex) {
        return new ResponseEntity<>("Null error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Global model attribute

    /**
     * Adds a version attribute to all models.
     * @return
     */
    @ModelAttribute("version")
    public String addVersion() {
        return "1.0.0";
    }

    // Global data binding

    /**
     * Prevents binding to fields named id in request parameters.
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id"); // Prevent binding to 'id' field
    }
}
