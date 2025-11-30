package com.github.carlossnogueira.inventorylite.domain.repositories;

import com.github.carlossnogueira.inventorylite.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
