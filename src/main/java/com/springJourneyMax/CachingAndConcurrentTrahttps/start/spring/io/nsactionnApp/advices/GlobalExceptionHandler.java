package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.advices;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlesResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
