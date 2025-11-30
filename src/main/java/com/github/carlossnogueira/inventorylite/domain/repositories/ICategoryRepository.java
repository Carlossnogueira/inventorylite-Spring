package com.github.carlossnogueira.inventorylite.domain.repositories;

import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    Category findByName(String name);

    @Transactional
    void deleteByName(String name);
}
