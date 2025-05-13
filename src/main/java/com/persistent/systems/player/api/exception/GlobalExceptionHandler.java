package com.persistent.systems.player.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException noSuchElementException, WebRequest webRequest) {
    String errorMessage = String.format("Requested resource not found: %s", noSuchElementException.getMessage());
    log.warn(errorMessage);
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.TEXT_PLAIN)
        .body(errorMessage);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<Object> handleDataAccessException(DataAccessException dataAccessException, WebRequest webRequest) {
    String errorMessage = String.format("Database error: %s", dataAccessException.getMessage());
    log.error(errorMessage);
    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
    String errorMessage = String.format("An Unexpected error occurred: %s", ex.getMessage());
    log.error(errorMessage);
    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
