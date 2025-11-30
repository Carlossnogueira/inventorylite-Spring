package com.github.carlossnogueira.inventorylite.domain.repositories;

import com.github.carlossnogueira.inventorylite.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
