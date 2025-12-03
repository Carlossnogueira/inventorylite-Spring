package com.github.carlossnogueira.inventorylite.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserJson {

    private String name;

    @Email
    private String email;

    private String password;
}
