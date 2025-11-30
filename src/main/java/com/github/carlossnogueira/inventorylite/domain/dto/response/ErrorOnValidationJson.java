package com.github.carlossnogueira.inventorylite.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ErrorOnValidationJson {

    private final List<String> errors;

    public ErrorOnValidationJson(String message){
        this.errors = List.of(message);
    }

    public ErrorOnValidationJson(List<String> messages){
        this.errors = messages;
    }

}
