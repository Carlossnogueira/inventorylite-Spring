package com.github.carlossnogueira.inventorylite.api.filter;

import com.github.carlossnogueira.inventorylite.api.exception.InventoryLiteException;
import com.github.carlossnogueira.inventorylite.domain.dto.response.ErrorOnValidationJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.FieldErrorJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorOnValidationJson> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorOnValidationJson(errors));
    }

    @ExceptionHandler(InventoryLiteException.class)
    public ResponseEntity<ErrorOnValidationJson> handleInventoryLiteException(InventoryLiteException ex) {
        var response = new ErrorOnValidationJson(ex.getErrors());
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorOnValidationJson> handleUnknowException() {
        var response = new ErrorOnValidationJson(List.of("Unknow error."));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(response);
    }

}
