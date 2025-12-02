package com.github.carlossnogueira.inventorylite.domain.dto.request;

import com.github.carlossnogueira.inventorylite.domain.entities.enums.Type;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryMovementsJson {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    @Max(99999)
    private Integer quantity;

    @NotNull
    private Type type;

    private String description;
}
