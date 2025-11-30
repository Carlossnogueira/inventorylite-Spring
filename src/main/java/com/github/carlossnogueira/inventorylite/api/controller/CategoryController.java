package com.github.carlossnogueira.inventorylite.api.controller;

import com.github.carlossnogueira.inventorylite.api.application.service.Category.CategoryService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CreateCategorySuccessJson> createCategory(@RequestBody CreateCategoryJson request){
        var result = categoryService.create(request);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping
    public ResponseEntity<CreateCategorySuccessJson> searchByName(@RequestParam String name) {
        var result =  categoryService.searchByName(name);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        categoryService.deleteByEmail(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CreateCategorySuccessJson> updateByName(
            @RequestBody CreateCategoryJson request,
            @RequestParam String name){
        var result = categoryService.update(name, request);
        return ResponseEntity.ok().body(result);
    }

}
