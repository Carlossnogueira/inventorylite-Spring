package com.github.carlossnogueira.inventorylite.domain.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UpdateProductJson {

    private String name;

    @Positive
    private Double price;

    @Positive
    private Integer quantity;

    @Positive
    private Integer categoryId;
}
