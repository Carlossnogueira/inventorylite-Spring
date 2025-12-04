package com.github.carlossnogueira.inventorylite.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.carlossnogueira.inventorylite.api.application.service.User.UserService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateUserJson;
import com.github.carlossnogueira.inventorylite.domain.dto.request.UpdateUserJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateUserSuccessJson;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateUserJson request) {
        userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserSuccessJson> getById(@PathVariable Long id) {
        CreateUserSuccessJson result = userService.searchById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{email}")
    public ResponseEntity<CreateUserSuccessJson> getByEmail(@PathVariable String email) {
        CreateUserSuccessJson result = userService.searchByEmail(email);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateUserSuccessJson> update(@PathVariable Long id, @Valid @RequestBody UpdateUserJson request) {
        CreateUserSuccessJson result = userService.update(id, request);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
