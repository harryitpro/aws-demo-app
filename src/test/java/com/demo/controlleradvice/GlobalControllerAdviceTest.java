package com.demo.controlleradvice;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalControllerAdviceTest {

    private GlobalControllerAdvice controllerAdvice;
    private HttpServletRequest request;
    private WebDataBinder binder;

    @BeforeEach
    void setUp() {
        controllerAdvice = new GlobalControllerAdvice();
        request = mock(HttpServletRequest.class);
        binder = mock(WebDataBinder.class);
    }

    // Test NullPointerException handling
    @Test
    void testHandleNullPointer() {
        NullPointerException ex = new NullPointerException("Test null pointer");
        ResponseEntity<String> response = controllerAdvice.handleNullPointer(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Null error: Test null pointer", response.getBody());
    }

    // Test NoHandlerFoundException handling
    @Test
    void testHandleNotFoundException() {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/test", null);
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Not Found", errorResponse.getType());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());

        assertEquals("No endpoint GET /test.", errorResponse.getMessage());
        assertEquals("/test", errorResponse.getPath());
        assertNotNull(errorResponse.getTimestamp());
    }

    // Test generic Exception handling
    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Unexpected error");
        when(request.getRequestURI()).thenReturn("/error");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Internal Server Error", errorResponse.getType());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
        assertEquals("An unexpected error occurred", errorResponse.getMessage());
        assertEquals("/error", errorResponse.getPath());
        assertNotNull(errorResponse.getTimestamp());
    }

    // Test ModelAttribute for version
    @Test
    void testAddVersion() {
        String version = controllerAdvice.addVersion();
        assertEquals("1.0.0", version);
    }

    // Test InitBinder for disallowing 'id' field
    @Test
    void testInitBinder() {
        controllerAdvice.initBinder(binder);
        verify(binder).setDisallowedFields("id");
    }

    // Test ErrorResponse class
    @Test
    void testErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse("TestError", 400, "Bad request", "/bad");

        assertEquals("TestError", errorResponse.getType());
        assertEquals(400, errorResponse.getStatus());
        assertEquals("Bad request", errorResponse.getMessage());
        assertEquals("/bad", errorResponse.getPath());
        assertNotNull(errorResponse.getTimestamp());
    }
}