package com.github.carlossnogueira.inventorylite.api.application.service.InventoryMovements;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.carlossnogueira.inventorylite.api.exception.BussinesValidationException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateInventoryMovementsJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateInventoryMovementsSuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.InventoryMovements;
import com.github.carlossnogueira.inventorylite.domain.entities.Product;
import com.github.carlossnogueira.inventorylite.domain.entities.enums.Type;
import com.github.carlossnogueira.inventorylite.domain.repositories.IInventoryMovements;
import com.github.carlossnogueira.inventorylite.domain.repositories.IProductRepository;

@Service
public class InventoryMovementsService {

    private final IInventoryMovements inventoryMovementsRepository;
    private final IProductRepository productRepository;

    public InventoryMovementsService(IInventoryMovements inventoryMovementsRepository,
            IProductRepository productRepository) {
        this.inventoryMovementsRepository = inventoryMovementsRepository;
        this.productRepository = productRepository;
    }

    public void create(CreateInventoryMovementsJson request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BussinesValidationException(List.of("Product doesn't exist")));

        int reqQuantity = request.getQuantity();
        int total = reqQuantity + product.getQuantity();
        int minus = product.getQuantity() - request.getQuantity();


        if (request.getType() == Type.IN) {
            if (reqQuantity <= 0 || reqQuantity > 9999 || total > 9999) {
                throw new BussinesValidationException(List.of("Invalid quantity"));
            }
            
            product.setQuantity(product.getQuantity() + reqQuantity);
        }

        if(request.getType() == Type.OUT){
            if(minus < 0 || reqQuantity > 9999 ){
                throw new BussinesValidationException(List.of("This product have " + product.getQuantity() + " and you quanto to remove " + request.getQuantity()));
            }

           product.setQuantity(product.getQuantity() - reqQuantity);
        }

        productRepository.saveAndFlush(product);

        InventoryMovements movement = InventoryMovements.builder()
                .productId(product.getId())
                .product(product)
                .quantity(request.getQuantity())
                .type(request.getType())
                .description(request.getDescription())
                .build();

        inventoryMovementsRepository.saveAndFlush(movement);
    }

    public CreateInventoryMovementsSuccessJson searchById(Long id) {

        InventoryMovements movement = inventoryMovementsRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(List.of("Inventory movement doesn't exist")));

        return CreateInventoryMovementsSuccessJson.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .quantity(movement.getQuantity())
                .type(movement.getType())
                .description(movement.getDescription())
                .createdAt(movement.getCreatedAt())
                .build();
    }

    public void deleteById(Long id) {
        InventoryMovements movement = inventoryMovementsRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(List.of("Inventory movement doesn't exist")));

        inventoryMovementsRepository.delete(movement);
    }
}
