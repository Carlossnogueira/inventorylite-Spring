package com.github.carlossnogueira.inventorylite.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class AuthenticateUserJson {

    @Email
    private String email;

    @NotBlank
    private String password;

}
