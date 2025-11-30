package com.github.carlossnogueira.inventorylite.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class CreateCategorySuccessJson {
    private String name;
}
