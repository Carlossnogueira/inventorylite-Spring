package com.github.carlossnogueira.inventorylite.api.exception.UserException;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.github.carlossnogueira.inventorylite.api.exception.InventoryLiteException;

public class UserAlreadyExistsException extends InventoryLiteException {

    private final List<String> errors;

    public UserAlreadyExistsException() {
        super("");
        this.errors = List.of("User already exists with this email");
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
