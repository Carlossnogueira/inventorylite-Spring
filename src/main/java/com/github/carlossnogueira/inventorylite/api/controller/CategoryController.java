package com.github.carlossnogueira.inventorylite.api.controller;

import com.github.carlossnogueira.inventorylite.api.application.service.Category.RegisterCategoryService;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final RegisterCategoryService registerCategoryService;

    @PostMapping
    public ResponseEntity<CreateCategorySuccessJson> createCategory(@RequestBody CreateCategoryJson request){
        var result = registerCategoryService.execute(request);
        return ResponseEntity.status(201).body(result);
    }
}
