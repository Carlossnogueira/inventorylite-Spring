package com.github.carlossnogueira.inventorylite.api.application.service.Category;

import com.github.carlossnogueira.inventorylite.api.exception.CategoryAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegisterCategoryService {

    private final ICategoryRepository _repository;

    public CreateCategorySuccessJson execute(CreateCategoryJson request){
        var categoryAlreadyExists = _repository.existsByName(request.getName());

        if (categoryAlreadyExists)
            throw new CategoryAlreadyExistsException();

        Category category = Category.builder()
                .name(request.getName())
                .build();

        _repository.save(category);

        return new CreateCategorySuccessJson(request.getName());
    }
}
