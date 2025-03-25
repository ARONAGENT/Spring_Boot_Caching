package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.advices;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Exceptions.MySQLTransactionRollbackException;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlesResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlesRunTimeException(RuntimeException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MySQLTransactionRollbackException.class)
    public ResponseEntity<String> handleTransactionRollbackException(MySQLTransactionRollbackException ex) {
        return new ResponseEntity<>("Transaction Rolled Back: ", HttpStatus.CONFLICT);
    }

}
