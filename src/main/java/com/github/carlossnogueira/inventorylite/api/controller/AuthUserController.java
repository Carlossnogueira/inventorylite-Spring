package com.github.carlossnogueira.inventorylite.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.carlossnogueira.inventorylite.api.application.service.User.AuthUserService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.AuthenticateUserJson;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthUserController {

    private final AuthUserService service;

   
    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody AuthenticateUserJson request) {
        try {
            var result = this.service.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
