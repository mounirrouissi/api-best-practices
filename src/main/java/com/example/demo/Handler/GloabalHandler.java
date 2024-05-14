package com.example.demo.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloabalHandler  {
    
    @ExceptionHandler(value = {AnimalNotFound.class})
    public ResponseEntity<Object> handleAnimalNotFound(AnimalNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception exception) {

        
    
        return new ResponseEntity<>("Unexpected error occurred: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }   

    @ExceptionHandler(AnimalSaveException.class)
    public ResponseEntity<Object> handleAnimalSaveException(AnimalSaveException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    
}
