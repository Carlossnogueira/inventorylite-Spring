package com.github.carlossnogueira.inventorylite.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDto {

    @NotBlank(message = "Name is required.")
    private String name;
}
