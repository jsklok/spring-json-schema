package com.example.demo.system.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 */
@ControllerAdvice
public class CustomExceptionHandler {
    // --------------------------------------------------------------------------------------------

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorMessage> handleJsonParseExceptions(JsonParseException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage("schema is not a json format", null));
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationExceptions(ConstraintViolationException e) {
        var errors = e.getConstraintViolations().stream()
            .map(violation -> new ErrorMessage.ErrorItem(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            ))
            .toList();
        return ResponseEntity.badRequest().body(new ErrorMessage("their are constraints violated in your request", errors));
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream()
            .map(error -> new ErrorMessage.ErrorItem(
                error.getField(),
                error.getDefaultMessage()
            ))
            .toList();
        return ResponseEntity.badRequest().body(new ErrorMessage("their are constraints violated in your request", errors));
    }




    /**
     *
     * @param e
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorMessage("an error occurred", null));
    }

}
