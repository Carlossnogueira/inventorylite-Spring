package com.github.carlossnogueira.inventorylite.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BussinesValidationException extends InventoryLiteException {

    private final List<String> errors;

    public BussinesValidationException(List<String> errors) {
        super("");
        this.errors = errors;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

}
