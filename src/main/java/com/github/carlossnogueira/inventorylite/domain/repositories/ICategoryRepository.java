package com.github.carlossnogueira.inventorylite.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.carlossnogueira.inventorylite.domain.entities.Category;

import jakarta.transaction.Transactional;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);

    @Transactional
    void deleteByName(String name);

}
