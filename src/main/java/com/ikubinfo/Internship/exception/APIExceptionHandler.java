package com.ikubinfo.Internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(value = {ExistsReqException.class, PersistException.class})
    public final ResponseEntity<Object> badRequest(Exception e){
        APIException exception = new APIException(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundReqException.class)
    public final ResponseEntity<Object> entityNotFound(Exception e){
        APIException exception = new APIException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

}
