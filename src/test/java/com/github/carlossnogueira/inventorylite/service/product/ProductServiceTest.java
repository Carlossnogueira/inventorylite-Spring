package com.github.carlossnogueira.inventorylite.service.product;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.carlossnogueira.inventorylite.api.application.service.Product.ProductService;
import com.github.carlossnogueira.inventorylite.api.exception.BussinesValidationException;
import com.github.carlossnogueira.inventorylite.api.exception.ProductException.ProductAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateProductJson;
import com.github.carlossnogueira.inventorylite.domain.dto.request.UpdateProductJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateProductSuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.entities.Product;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;
import com.github.carlossnogueira.inventorylite.domain.repositories.IProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private IProductRepository productRepository;

    // -------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() {

        CreateProductJson product = new CreateProductJson("Apple", 1.25, 12, 2);

        Category category = new Category();
        category.setId(2);

        Mockito.when(productRepository.existsByName("Apple")).thenReturn(false);
        Mockito.when(categoryRepository.findById(2)).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> service.create(product));

        Mockito.verify(productRepository, Mockito.times(1))
                .existsByName("Apple");

        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(2);

        Mockito.verify(productRepository, Mockito.times(1))
                .saveAndFlush(Mockito.any(Product.class));
    }

    @Test
    @DisplayName("Should throw ProductAlreadyExistsException when product already exists")
    void shouldThrowProductAlreadyExistsExceptionWhenProductAlreadyExists() {

        CreateProductJson product = new CreateProductJson("Apple", 1.25, 12, 2);

        Category category = new Category();
        category.setId(2);

        Mockito.when(productRepository.existsByName("Apple")).thenReturn(true);
        Mockito.when(categoryRepository.findById(2)).thenReturn(Optional.of(category));

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> service.create(product));

        Mockito.verify(productRepository, Mockito.times(1))
                .existsByName("Apple");

        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(2);

        Mockito.verify(productRepository, Mockito.never())
                .saveAndFlush(Mockito.any());
    }

    // -------------------------------------------------------------
    // DELETE BY ID
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should delete product when it exists")
    void shouldDeleteProductWhenItExists() {

        Product product = new Product(
                10L, "Apple", 2.50, 15, LocalDateTime.now(), 3, new Category());

        Mockito.when(productRepository.findById(10L))
                .thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> service.deleteById(10L));

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(10L);

        Mockito.verify(productRepository, Mockito.times(1))
                .delete(product);
    }


    @Test
    @DisplayName("Should throw BussinesValidationException when product does not exist by id")
    void shouldThrowBussinessExceptionWhenProductDoesNotExistById() {

        Mockito.when(productRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(
                BussinesValidationException.class,
                () -> service.deleteById(10L));

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(10L);

        Mockito.verify(productRepository, Mockito.never())
                .delete(Mockito.any());
    }

    // -------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should update product when it exists")
    void shouldUpdateProductWhenItExists() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(2.50);
        product.setCategory(new Category());

        UpdateProductJson updateRequest = new UpdateProductJson();
        updateRequest.setName("Banana");
        updateRequest.setPrice(3.00);
        updateRequest.setCategoryId(5);

        Category newCategory = new Category();
        newCategory.setId(5);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        Mockito.when(categoryRepository.findById(5))
                .thenReturn(Optional.of(newCategory));

        CreateProductSuccessJson result = service.update(1L, updateRequest);

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(1L);

        Mockito.verify(categoryRepository, Mockito.times(1))
                .findById(5);

        Mockito.verify(productRepository, Mockito.times(1))
                .saveAndFlush(product);

        assertEquals("Banana", product.getName());
        assertEquals(3.00, product.getPrice());
        assertEquals(newCategory, product.getCategory());
    }

    @Test
    @DisplayName("Should throw BussinesValidationException when updating non-existing product")
    void shouldThrowBussinessExceptionWhenUpdatingNonExistingProduct() {

        UpdateProductJson updateRequest = new UpdateProductJson();
        updateRequest.setName("Banana");

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                BussinesValidationException.class,
                () -> service.update(1L, updateRequest));

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(1L);

        Mockito.verify(productRepository, Mockito.never())
                .saveAndFlush(Mockito.any());
    }

    // -------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should return product when searching by id and it exists")
    void shouldReturnProductWhenSearchingByIdAndItExists() {

        Category category = new Category();
        category.setId(2);

        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(2.50);
        product.setQuantity(10);
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        CreateProductSuccessJson result = service.searchById(1L);

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(1L);

        assertEquals("Apple", result.getName());
        assertEquals(2.50, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals(2, result.getCategoryId());
    }

    @Test
    @DisplayName("Should throw BussinesValidationException when searching by id and product does not exist")
    void shouldThrowBussinessExceptionWhenSearchingByIdAndProductDoesNotExist() {

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                BussinesValidationException.class,
                () -> service.searchById(1L));

        Mockito.verify(productRepository, Mockito.times(1))
                .findById(1L);
    }

}
