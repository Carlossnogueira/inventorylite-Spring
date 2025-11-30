package com.github.carlossnogueira.inventorylite.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoryAlreadyExistsException extends InventoryLiteException {
    private final List<String> errors;

    public CategoryAlreadyExistsException() {
        super("");
        this.errors = List.of("Category already exists");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public List<String> getErrors() {
        return this.errors;
    }
}
