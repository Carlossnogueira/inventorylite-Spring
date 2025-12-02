package com.github.carlossnogueira.inventorylite.domain.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateCategoryJson {

    @NotBlank
    @Length(min=2, max=50)
    private String name;
}
