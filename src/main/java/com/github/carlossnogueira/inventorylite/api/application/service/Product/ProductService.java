package com.github.carlossnogueira.inventorylite.api.application.service.Product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.carlossnogueira.inventorylite.api.exception.BussinesValidationException;
import com.github.carlossnogueira.inventorylite.api.exception.ProductException.ProductAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateProductJson;
import com.github.carlossnogueira.inventorylite.domain.dto.request.UpdateProductJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateProductSuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import com.github.carlossnogueira.inventorylite.domain.entities.Product;
import com.github.carlossnogueira.inventorylite.domain.repositories.ICategoryRepository;
import com.github.carlossnogueira.inventorylite.domain.repositories.IProductRepository;

@Service
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void create(CreateProductJson request) {
        boolean productExists = productRepository.existsByName(request.getName());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BussinesValidationException(List.of("Category doesn't exist")));

        if (productExists)
            throw new ProductAlreadyExistsException();

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .categoryId(category.getId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .updatedAt(LocalDateTime.now())
                .build();

        productRepository.saveAndFlush(product);
    }

    public CreateProductSuccessJson searchByName(String name) {
        Product productExists = productRepository.findByName(name)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("Product doesn't exist")));

        if (productExists == null)
            throw new BussinesValidationException(List.of("Product don't exist"));

        return CreateProductSuccessJson.builder()
                .id(productExists.getId())
                .name(productExists.getName())
                .quantity(productExists.getQuantity())
                .price(productExists.getPrice())
                .categoryId(productExists.getCategoryId())
                .updatedAt(productExists.getUpdatedAt())
                .build();
    }

    public CreateProductSuccessJson searchById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("Product doesn't exist")));

        return CreateProductSuccessJson.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public void deleteById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("Product doesn't exist")));

        productRepository.delete(product);
    }

    public void deleteByName(String name) {

        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("Product doesn't exist")));

        productRepository.delete(product);
    }


    public CreateProductSuccessJson update(Long id, UpdateProductJson updatedData) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(List.of("Product not found")));

        if (updatedData.getCategoryId() != null) {
            Category category = categoryRepository.findById(updatedData.getCategoryId())
                    .orElseThrow(() -> new BussinesValidationException(List.of("Category not exists")));

            product.setCategoryId(category.getId());
            product.setCategory(category);
        }

        if (updatedData.getName() != null) {
            product.setName(updatedData.getName());
        }

        if (updatedData.getPrice() != null) {
            product.setPrice(updatedData.getPrice());
        }

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.saveAndFlush(product);

        return CreateProductSuccessJson.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
