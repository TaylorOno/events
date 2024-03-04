package com.practical.events.exceptions;

import com.practical.events.dtos.EventRequest;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleEventNotFoundException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleEventValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, GlobalExceptionHandler::getErrorMessage));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private static String getErrorMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "unknown error";
    }
}
