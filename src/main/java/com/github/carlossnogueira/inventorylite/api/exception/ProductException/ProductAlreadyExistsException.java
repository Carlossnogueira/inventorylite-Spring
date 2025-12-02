package com.github.carlossnogueira.inventorylite.api.exception.ProductException;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.github.carlossnogueira.inventorylite.api.exception.InventoryLiteException;

public class ProductAlreadyExistsException extends InventoryLiteException{

private final List<String> errors;

    public ProductAlreadyExistsException() {
        super("");
        this.errors = List.of("Product already exists");
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
