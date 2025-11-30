package com.github.carlossnogueira.inventorylite.domain.repositories;

import com.github.carlossnogueira.inventorylite.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);
}
