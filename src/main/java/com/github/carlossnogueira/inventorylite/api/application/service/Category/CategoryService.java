package com.github.carlossnogueira.inventorylite.api.application.service.Category;

import org.springframework.stereotype.Service;

import com.github.carlossnogueira.inventorylite.api.exception.CategoryException.CategoryAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.api.exception.NotFoundException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

    private final ICategoryRepository repository;

    public void create(CreateCategoryJson request) {

        if(repository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException();
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();

        repository.saveAndFlush(category);
    }

    public CreateCategorySuccessJson update(String name, CreateCategoryJson category) {

        Category categoryEntity = repository.findByName(name)
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
