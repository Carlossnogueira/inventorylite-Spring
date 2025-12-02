package com.github.carlossnogueira.inventorylite.service.inventorymovements;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.carlossnogueira.inventorylite.api.application.service.InventoryMovements.InventoryMovementsService;
import com.github.carlossnogueira.inventorylite.api.exception.BussinesValidationException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateInventoryMovementsJson;
import com.github.carlossnogueira.inventorylite.domain.entities.InventoryMovements;
import com.github.carlossnogueira.inventorylite.domain.entities.Product;
import com.github.carlossnogueira.inventorylite.domain.entities.enums.Type;
import com.github.carlossnogueira.inventorylite.domain.repositories.IInventoryMovements;
import com.github.carlossnogueira.inventorylite.domain.repositories.IProductRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryMovementsServiceTest {

    @InjectMocks
    private InventoryMovementsService service;

    @Mock
    private IInventoryMovements inventoryMovementsRepository;

    @Mock
    private IProductRepository productRepository;

    // -------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should create inventory movement successfully")
    void shouldCreateInventoryMovementSuccessfully() {

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 10, Type.IN, "Test movement");

        Product product = new Product();
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> service.create(request));

        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).saveAndFlush(product);
        Mockito.verify(inventoryMovementsRepository, Mockito.times(1))
                .saveAndFlush(Mockito.any(InventoryMovements.class));
    }

    @Test
    @DisplayName("Should increase quantity when IN is valid")
    void shouldIncreaseQuantity() {

        Product product = Product.builder()
                .quantity(10)
                .id(1L)
                .build();

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 5, Type.IN, "Valid IN");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.saveAndFlush(product)).thenReturn(product);

        service.create(request);
        assertEquals(15, product.getQuantity());
    }

    @Test
    @DisplayName("Should decrease quantity when OUT is valid")
    void shouldDecreaseQuantity() {

        Product product = Product.builder()
                .quantity(10)
                .id(1L)
                .build();

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 3, Type.OUT, "Valid OUT");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.saveAndFlush(product)).thenReturn(product);

        service.create(request);

        assertEquals(7, product.getQuantity());
    }

    @Test
    @DisplayName("Should throw BussinesValidationException when product does not exist")
    void shouldThrowBussinessExceptionWhenProductDoesNotExist() {

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 10, Type.IN, "Test movement");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BussinesValidationException.class, () -> service.create(request));

        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(inventoryMovementsRepository, Mockito.never()).saveAndFlush(Mockito.any());
    }

    @Test
    @DisplayName("Should throw when IN movement exceeds max stock")
    void shouldThrowWhenTotalExceedsLimit() {
        Product product = Product.builder()
                .quantity(9000)
                .id(1L)
                .build();

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 2000, Type.IN, "Test");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BussinesValidationException.class,
                () -> service.create(request));
    }

    @Test
    @DisplayName("Should throw when OUT movement exceeds min stock")
    void ShouldThownWhenExceedsMinStock() {
        Product product = Product.builder()
                .quantity(1)
                .id(1l)
                .build();

        CreateInventoryMovementsJson request = new CreateInventoryMovementsJson(1L, 2000, Type.OUT, "Test");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BussinesValidationException.class,
                () -> service.create(request));
    }

    // -------------------------------------------------------------
    // SEARCH BY ID
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should return inventory movement when it exists")
    void shouldReturnInventoryMovementWhenItExists() {

        InventoryMovements movement = new InventoryMovements();
        movement.setId(1L);
        movement.setProductId(1L);
        movement.setQuantity(10);
        movement.setType(Type.IN);
        movement.setDescription("Test");
        movement.setCreatedAt(LocalDateTime.now());

        Mockito.when(inventoryMovementsRepository.findById(1L)).thenReturn(Optional.of(movement));

        assertDoesNotThrow(() -> service.searchById(1L));

        Mockito.verify(inventoryMovementsRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw BussinesValidationException when inventory movement does not exist by id")
    void shouldThrowBussinessExceptionWhenInventoryMovementDoesNotExistById() {

        Mockito.when(inventoryMovementsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BussinesValidationException.class, () -> service.searchById(1L));

        Mockito.verify(inventoryMovementsRepository, Mockito.times(1)).findById(1L);
    }

    // -------------------------------------------------------------
    // DELETE BY ID
    // -------------------------------------------------------------
    @Test
    @DisplayName("Should delete inventory movement when it exists")
    void shouldDeleteInventoryMovementWhenItExists() {

        InventoryMovements movement = new InventoryMovements();
        movement.setId(1L);

        Mockito.when(inventoryMovementsRepository.findById(1L)).thenReturn(Optional.of(movement));

        assertDoesNotThrow(() -> service.deleteById(1L));

        Mockito.verify(inventoryMovementsRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(inventoryMovementsRepository, Mockito.times(1)).delete(movement);
    }

    @Test
    @DisplayName("Should throw BussinesValidationException when inventory movement does not exist by id for delete")
    void shouldThrowBussinessExceptionWhenInventoryMovementDoesNotExistByIdForDelete() {

        Mockito.when(inventoryMovementsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BussinesValidationException.class, () -> service.deleteById(1L));

        Mockito.verify(inventoryMovementsRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(inventoryMovementsRepository, Mockito.never()).delete(Mockito.any());
    }
}
