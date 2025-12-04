package com.github.carlossnogueira.inventorylite.api.exception.UserException;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.github.carlossnogueira.inventorylite.api.exception.InventoryLiteException;

public class EmailOrPasswordIncorrectException extends InventoryLiteException {

    private final List<String> errors;

    public EmailOrPasswordIncorrectException() {
        super("");
        this.errors = List.of("Email or password incorrect!");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    @Override
    public List<String> getErrors() {
        return this.errors;
    }

}
