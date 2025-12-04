package com.github.carlossnogueira.inventorylite.api.controller;

import com.github.carlossnogueira.inventorylite.api.application.service.Category.CategoryService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryJson category, HttpServletRequest request){
        categoryService.create(category, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CreateCategorySuccessJson> searchByName(@RequestParam String name) {
        var result =  categoryService.searchByName(name);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@PathVariable String name) {
        categoryService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CreateCategorySuccessJson> updateById(
            @Valid @RequestBody CreateCategoryJson request,
            @PathVariable int id){
        var result = categoryService.update(id, request);
        return ResponseEntity.ok().body(result);
    }

}
