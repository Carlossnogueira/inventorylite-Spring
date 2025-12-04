package com.github.carlossnogueira.inventorylite.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.carlossnogueira.inventorylite.api.application.service.InventoryMovements.InventoryMovementsService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateInventoryMovementsJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateInventoryMovementsSuccessJson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("inventory-movements")
@AllArgsConstructor
public class InventoryMovementsController {

    private final InventoryMovementsService service;

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateInventoryMovementsJson movementRequest, HttpServletRequest request) {
        service.create(movementRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{/id}")
    public ResponseEntity<CreateInventoryMovementsSuccessJson> getById(@PathVariable Long id) {
        var result = service.searchById(id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteById(@PathVariable  Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
