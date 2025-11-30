package com.github.carlossnogueira.inventorylite.service.category;

import com.github.carlossnogueira.inventorylite.api.application.service.Category.CategoryService;
import com.github.carlossnogueira.inventorylite.api.exception.CategoryException.CategoryAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.api.exception.NotFoundException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateCategoryJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateCategorySuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private ICategoryRepository repository;

    @Test
    @DisplayName("Should create category successfully")
    void shouldCreateCategorySuccessfully(){

        CreateCategoryJson request = new CreateCategoryJson("Food");
        Mockito.when(repository.existsByName("Food")).thenReturn(false);

        CreateCategorySuccessJson response = service.create(request);

        assertEquals("Food", response.getName());

        Mockito.verify(repository, Mockito.times(1))
                .saveAndFlush(Mockito.any(Category.class));
    }

    @Test
    @DisplayName("Should throw Exception when category already exists")
    void shouldThrowExceptionWhenCategoryAlreadyExists() {

        CreateCategoryJson request = new CreateCategoryJson("Food");

        Mockito.when(repository.existsByName("Food")).thenReturn(true);


        assertThrows(
                CategoryAlreadyExistsException.class,
                () -> service.create(request)
        );

        Mockito.verify(repository, Mockito.never())
                .saveAndFlush(Mockito.any());
    }

    @Test
    @DisplayName("Should delete category when it exists")
    void shouldDeleteCategoryWhenExists() {

        Category category = new Category();
        category.setName("Toys");

        Mockito.when(repository.findByName("Toys"))
                .thenReturn(category);

        service.deleteByEmail("Toys");

        Mockito.verify(repository, Mockito.times(1))
                .deleteByName("Toys");
    }

    @Test
    @DisplayName("Should throw NotFoundException when category does not exist")
    void shouldThrowExceptionWhenCategoryDoesNotExist() {

        Mockito.when(repository.findByName("Toys"))
                .thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> service.deleteByEmail("Toys"));

        Mockito.verify(repository, Mockito.never())
                .deleteByName(Mockito.anyString());
    }

    @Test
    @DisplayName("Should update category when it exists")
    void shouldUpdateCategoryWhenExists() {

        Category category = new Category();
        category.setName("Toys");

        CreateCategoryJson request = new CreateCategoryJson("Devices");


        Mockito.when(repository.findByName("Toys"))
                .thenReturn(category);


        CreateCategorySuccessJson response = service.update("Toys", request);

        Mockito.verify(repository, Mockito.times(1))
                .findByName("Toys");

        assertEquals("Devices", category.getName());

        Mockito.verify(repository, Mockito.times(1))
                .saveAndFlush(category);

        assertEquals("Devices", response.getName());
    }

    @Test
    @DisplayName("Should get a category by name")
    void shouldGetACategoryByName() {

        Category category = new Category();
        category.setName("Toys");

        Mockito.when(repository.findByName("Toys"))
                .thenReturn(category);

        CreateCategorySuccessJson result = service.searchByName("Toys");

        assertEquals("Toys", result.getName());

        Mockito.verify(repository, Mockito.times(1))
                .findByName("Toys");
    }

    @Test
    @DisplayName("Should throw error when category is not found")
    void shouldThrowWhenCategoryNotFound() {

        Mockito.when(repository.findByName("Toys"))
                .thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> service.searchByName("Toys")
        );

        Mockito.verify(repository, Mockito.times(1))
                .findByName("Toys");
    }




}
