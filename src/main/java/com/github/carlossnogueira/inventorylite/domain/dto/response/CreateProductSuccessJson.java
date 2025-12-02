package com.github.carlossnogueira.inventorylite.domain.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductSuccessJson {

    private Long id;

    private String name;

    private Double price;

    private int quantity;

    private int categoryId;
    
    private LocalDateTime updatedAt;
}
