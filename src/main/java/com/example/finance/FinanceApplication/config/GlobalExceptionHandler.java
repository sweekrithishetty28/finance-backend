package com.example.finance.FinanceApplication.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
        for(FieldError error:fieldErrors){
            String fieldName=error.getField();
            String message=error.getDefaultMessage();
            errors.put(fieldName,message);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGeneralErrors(Exception ex){
            Map<String,String> error=new HashMap<>();
            error.put("message", ex.getMessage());
            return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
