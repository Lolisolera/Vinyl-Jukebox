package com.lola.vinyljukebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle unexpected server errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle errors from external HTTP APIs like Deezer
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex, WebRequest request) {
        return buildErrorResponse("External API Error: " + ex.getMessage(), ex.getStatusCode());
    }

    // Universal error response builder (supports HttpStatusCode too)
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatusCode status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.toString());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
