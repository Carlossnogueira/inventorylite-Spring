package com.github.carlossnogueira.inventorylite.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.carlossnogueira.inventorylite.domain.entities.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Optional<Product> findByName(String name);
    
}
