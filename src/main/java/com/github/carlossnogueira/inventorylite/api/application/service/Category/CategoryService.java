package com.github.carlossnogueira.inventorylite.api.application.service.Category;

import org.springframework.stereotype.Service;

import com.github.carlossnogueira.inventorylite.api.exception.CategoryException.CategoryAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.api.exception.NotFoundException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final ICategoryRepository repository;

    public void create(CreateCategoryJson categoryRequest, HttpServletRequest req) {

        if (repository.existsByName(categoryRequest.getName())) {
            throw new CategoryAlreadyExistsException();
        }

        var id = Long.parseLong(req.getAttribute("user_id").toString());

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .createdBy(id)
                .build();

        repository.saveAndFlush(category);
    }

    public CreateCategorySuccessJson update(int id, CreateCategoryJson category) {

        Category categoryEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found."));

        if (category.getName() != null) {
            categoryEntity.setName(category.getName());
        }

        repository.saveAndFlush(categoryEntity);

        return new CreateCategorySuccessJson(categoryEntity.getId(), categoryEntity.getName());
    }

    public CreateCategorySuccessJson searchByName(String name) {
        Category categoryExists = repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        CreateCategorySuccessJson category = CreateCategorySuccessJson
                .builder()
                .id(categoryExists.getId())
                .name(categoryExists.getName())
                .build();

        return category;
    }

    public void deleteByName(String name) {

        Category categoryExists = repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category not found."));

        repository.deleteByName(categoryExists.getName());
    }

    public void deleteById(int id) {
        Category categoryExists = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found."));

        repository.deleteById(id);
    }

}
