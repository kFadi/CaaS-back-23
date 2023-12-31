package com.jb.caas.advice;

/*
 * copyrights @ fadi
 */

import com.jb.caas.dto.ErrDto;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CouponControllerAdvice {

    // handle our security exceptions
    @ExceptionHandler(value = {CouponSecurityException.class})
    public ResponseEntity<?> handleSecException(CouponSecurityException e) {
        return new ResponseEntity<>(e.getSecMsg().getMsg(), e.getSecMsg().getStatus());
    }

    // handle our business logic  exceptions
    @ExceptionHandler(value = {CouponSystemException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrDto handleError(Exception e) {
        return new ErrDto(e.getMessage());
    }

    // handle our spring validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}