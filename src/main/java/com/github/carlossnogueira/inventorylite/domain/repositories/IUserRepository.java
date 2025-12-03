package com.github.carlossnogueira.inventorylite.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.carlossnogueira.inventorylite.domain.entities.User;

public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
