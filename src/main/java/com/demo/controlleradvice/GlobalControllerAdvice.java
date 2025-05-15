package com.demo.controlleradvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    // Global exception handling

    /**
     * Handles NullPointerException globally for controllers in com.example.controllers.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException ex) {
        return new ResponseEntity<>("Null error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle 404 Not Found errors
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle all other exceptions (e.g., unexpected errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Global model attribute

    /**
     * Adds a version attribute to all models.
     */
    @ModelAttribute("version")
    public String addVersion() {
        return "1.0.0";
    }

    // Global data binding

    /**
     * Prevents binding to fields named id in request parameters.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id"); // Prevent binding to 'id' field
    }
}
