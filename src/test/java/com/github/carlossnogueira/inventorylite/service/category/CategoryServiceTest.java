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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

        @InjectMocks
        private CategoryService service;

        @Mock
        private ICategoryRepository repository;

        // -------------------------------------------------------------
        // CREATE
        // -------------------------------------------------------------
        @Test
        @DisplayName("Should create category successfully")
        void shouldCreateCategorySuccessfully() {

                CreateCategoryJson request = new CreateCategoryJson("Food");

                Mockito.when(repository.existsByName("Food"))
                                .thenReturn(false);

                assertDoesNotThrow(() -> service.create(request));

                Mockito.verify(repository, Mockito.times(1))
                                .saveAndFlush(Mockito.any(Category.class));
        }

        @Test
        @DisplayName("Should throw Exception when category already exists")
        void shouldThrowExceptionWhenCategoryAlreadyExists() {

                CreateCategoryJson request = new CreateCategoryJson("Food");

                Mockito.when(repository.existsByName("Food"))
                                .thenReturn(true);

                assertThrows(
                                CategoryAlreadyExistsException.class,
                                () -> service.create(request));

                Mockito.verify(repository, Mockito.never())
                                .saveAndFlush(Mockito.any());
        }

        // -------------------------------------------------------------
        // DELETE BY NAME
        // -------------------------------------------------------------
        @Test
        @DisplayName("Should delete category when it exists")
        void shouldDeleteCategoryWhenExists() {

                Category category = new Category();
                category.setName("Toys");

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.of(category));

                service.deleteByName("Toys");

                Mockito.verify(repository, Mockito.times(1))
                                .deleteByName("Toys");
        }

        @Test
        @DisplayName("Should throw NotFoundException when category does not exist")
        void shouldThrowExceptionWhenCategoryDoesNotExist() {

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.empty());

                assertThrows(NotFoundException.class,
                                () -> service.deleteByName("Toys"));

                Mockito.verify(repository, Mockito.never())
                                .deleteByName(Mockito.anyString());
        }

        // -------------------------------------------------------------
        // DELETE BY ID
        // -------------------------------------------------------------
        @Test
        @DisplayName("Should delete category by ID when it exists")
        void shouldDeleteCategoryByIdWhenExists() {

                Category category = new Category();
                category.setId(10);
                category.setName("Sports");

                Mockito.when(repository.findById(10))
                                .thenReturn(Optional.of(category));

                service.deleteById(10);

                Mockito.verify(repository, Mockito.times(1))
                                .deleteById(10);
        }

        @Test
        @DisplayName("Should throw NotFoundException when deleting by ID and category not found")
        void shouldThrowExceptionWhenDeletingByIdNotFound() {

                Mockito.when(repository.findById(10))
                                .thenReturn(Optional.empty());

                assertThrows(NotFoundException.class,
                                () -> service.deleteById(10));

                Mockito.verify(repository, Mockito.never())
                                .deleteById(Mockito.anyInt());
        }

        // -------------------------------------------------------------
        // UPDATE
        // -------------------------------------------------------------
        @Test
        @DisplayName("Should update category when it exists")
        void shouldUpdateCategoryWhenExists() {

                Category category = new Category();
                category.setId(1);
                category.setName("Toys");

                CreateCategoryJson request = new CreateCategoryJson("Devices");

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.of(category));

                CreateCategorySuccessJson response = service.update("Toys", request);

                Mockito.verify(repository, Mockito.times(1))
                                .findByName("Toys");

                assertEquals("Devices", category.getName());

                Mockito.verify(repository, Mockito.times(1))
                                .saveAndFlush(category);

                assertEquals("Devices", response.getName());
        }

        @Test
        @DisplayName("Should throw NotFoundException when updating non-existing category")
        void shouldThrowExceptionWhenUpdatingNotFound() {

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.empty());

                assertThrows(
                                NotFoundException.class,
                                () -> service.update("Toys", new CreateCategoryJson("Devices"))
                );

                Mockito.verify(repository, Mockito.never())
                                .saveAndFlush(Mockito.any());
        }

        // -------------------------------------------------------------
        // SEARCH
        // -------------------------------------------------------------
        @Test
        @DisplayName("Should get a category by name")
        void shouldGetACategoryByName() {

                Category category = new Category();
                category.setId(1);
                category.setName("Toys");

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.of(category));

                CreateCategorySuccessJson result = service.searchByName("Toys");

                assertEquals("Toys", result.getName());

                Mockito.verify(repository, Mockito.times(1))
                                .findByName("Toys");
        }

        @Test
        @DisplayName("Should throw error when category is not found")
        void shouldThrowWhenCategoryNotFound() {

                Mockito.when(repository.findByName("Toys"))
                                .thenReturn(Optional.empty());

                assertThrows(
                                NotFoundException.class,
                                () -> service.searchByName("Toys"));

                Mockito.verify(repository, Mockito.times(1))
                                .findByName("Toys");
        }
}
