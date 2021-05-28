package com.ikubinfo.Internship.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class APIExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(value = PersistException.class)
    public final ResponseEntity<Object> persistException(Exception e){
        APIException exception = new APIException(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ExistsReqException.class)
    public final ResponseEntity<Object> badRequest(Exception e){
        APIException exception = new APIException(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundReqException.class)
    public final ResponseEntity<Object> entityNotFound(Exception e){
        APIException exception = new APIException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> validationException(MethodArgumentNotValidException e){
        String message = e.getBindingResult().getAllErrors().stream().map(
                error->error.getDefaultMessage()
        ).collect(Collectors.joining(", "));
        APIException exception = new APIException(message, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> accessException(AccessDeniedException e){
        String message = e.getMessage();
        APIException exception = new APIException(message, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> genericException(Exception e){
        APIException exception = new APIException(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        logger.debug(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
