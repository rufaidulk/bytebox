package com.rufaidulk.bytebox.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler 
{
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex)
    {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.UNAUTHORIZED);
        error.setMessage(ex.getMessage());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        ApiError error = new ApiError();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage("Validation Failure");
        
        return buildResponseEntity(error);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<?> handleValidationException(ValidationException ex)
    {
        ApiError error = new ApiError();
        error.getViolations().add(new Violation(ex.getField(), ex.getMessage()));

        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage("Validation Failure");
        
        return buildResponseEntity(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex)
    {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(ex.getMessage());

        return buildResponseEntity(error);
    }
    
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) 
    {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
