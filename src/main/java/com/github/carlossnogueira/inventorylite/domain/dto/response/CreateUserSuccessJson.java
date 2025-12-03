package com.github.carlossnogueira.inventorylite.domain.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserSuccessJson {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;
}
