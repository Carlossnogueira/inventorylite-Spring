package com.github.carlossnogueira.inventorylite.domain.dto.response;

import java.time.LocalDateTime;

import com.github.carlossnogueira.inventorylite.domain.entities.enums.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInventoryMovementsSuccessJson {

    private Long id;

    private Long productId;

    private Integer quantity;

    private Type type;

    private String description;

    private LocalDateTime createdAt;
}
