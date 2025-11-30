package com.github.carlossnogueira.inventorylite.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NotFoundException extends InventoryLiteException {
    private final List<String> errors;

    public NotFoundException(String message) {
        super("");
        this.errors = List.of(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }
}
