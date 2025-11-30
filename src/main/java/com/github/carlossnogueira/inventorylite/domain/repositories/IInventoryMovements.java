package com.github.carlossnogueira.inventorylite.domain.repositories;

import com.github.carlossnogueira.inventorylite.domain.entities.InventoryMovements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryMovements extends JpaRepository<InventoryMovements,Long> {
}
