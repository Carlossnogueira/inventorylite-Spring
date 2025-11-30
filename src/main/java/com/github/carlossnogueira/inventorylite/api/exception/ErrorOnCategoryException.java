package com.github.carlossnogueira.inventorylite.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorOnCategoryException extends InventoryLiteException {

    private final List<String> errors;

    public ErrorOnCategoryException(List<String> errorList) {
        super("");
        this.errors = errorList;
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
