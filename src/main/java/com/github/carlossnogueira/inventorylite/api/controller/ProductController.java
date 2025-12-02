package com.github.carlossnogueira.inventorylite.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.carlossnogueira.inventorylite.api.application.service.Product.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateProductJson;
import com.github.carlossnogueira.inventorylite.domain.dto.request.UpdateProductJson;

import org.springframework.web.bind.annotation.GetMapping;

import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateProductSuccessJson;


@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {
  
    private final ProductService service;

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateProductJson request) {
        service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateById(@Valid @RequestBody UpdateProductJson request, @RequestParam Long id) {
        service.update(id,request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteById(@RequestParam Long id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping()
    public ResponseEntity<CreateProductSuccessJson> getById(@RequestParam String name) {
        var result = service.searchByName(name);
        return ResponseEntity.ok().body(result);
    }
    
    
}
