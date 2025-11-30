package com.github.carlossnogueira.inventorylite.api.application.service.Category;

import com.github.carlossnogueira.inventorylite.api.exception.CategoryException.CategoryAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.api.exception.NotFoundException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CategoryService {

    private final ICategoryRepository _repository;

    public CreateCategorySuccessJson create(CreateCategoryJson request){
        var categoryAlreadyExists = _repository.existsByName(request.getName());

        if (categoryAlreadyExists)
            throw new CategoryAlreadyExistsException();

        Category category = Category.builder()
                .name(request.getName())
                .build();

        _repository.saveAndFlush(category);

        return new CreateCategorySuccessJson(request.getName());
    }

    public CreateCategorySuccessJson update(String name, CreateCategoryJson category){
        var categoryEntity = _repository.findByName(name);

        if (categoryEntity == null)
            throw new NotFoundException("Category not found.");


        if (category.getName() != null) {
            categoryEntity.setName(category.getName());
        }

        _repository.saveAndFlush(categoryEntity);

        return new CreateCategorySuccessJson(categoryEntity.getName());
    }

    public CreateCategorySuccessJson searchByName(String name){
        var categoryExists = _repository.findByName(name);

        if (categoryExists == null)
            throw new NotFoundException("Category not found.");

        CreateCategorySuccessJson category = CreateCategorySuccessJson
                .builder()
                .name(categoryExists.getName())
                .build();

        return category;
    }

    public void deleteByEmail(String name){
        var categoryExists = _repository.findByName(name);

        if (categoryExists == null)
            throw new NotFoundException("Category not found.");

        _repository.deleteByName(categoryExists.getName());
    }

}
