package com.github.carlossnogueira.inventorylite.api.exception;

import java.util.List;

public abstract class InventoryLiteException extends RuntimeException{

    public InventoryLiteException(String message){
        super(message);
    }

    public abstract int getStatusCode();
    public abstract List<String> getErrors();

}
