package com.github.carlossnogueira.inventorylite.domain.entities;

import java.time.LocalDateTime;

import com.github.carlossnogueira.inventorylite.domain.entities.enums.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class InventoryMovements {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @NotNull
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    private String description;

    private LocalDateTime createdAt;

    @Column(name = "moved_by")
    private Long movedBy;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
