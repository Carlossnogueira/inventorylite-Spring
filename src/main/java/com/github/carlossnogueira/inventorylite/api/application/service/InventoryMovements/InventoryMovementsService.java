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

import jakarta.servlet.http.HttpServletRequest;

@Service
public class InventoryMovementsService {

    private final IInventoryMovements inventoryMovementsRepository;
    private final IProductRepository productRepository;

    public InventoryMovementsService(IInventoryMovements inventoryMovementsRepository,
            IProductRepository productRepository) {
        this.inventoryMovementsRepository = inventoryMovementsRepository;
        this.productRepository = productRepository;
    }

    public void create(CreateInventoryMovementsJson productRequest, HttpServletRequest request) {
        Product product = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new BussinesValidationException(List.of("Product doesn't exist")));

       
        int reqQuantity = productRequest.getQuantity();
        int total = reqQuantity + product.getQuantity();
        int minus = product.getQuantity() - productRequest.getQuantity();


        if (productRequest.getType() == Type.IN) {
            if (reqQuantity <= 0 || reqQuantity > 9999 || total > 9999) {
                throw new BussinesValidationException(List.of("Invalid quantity"));
            }
            
            product.setQuantity(product.getQuantity() + reqQuantity);
        }

        if(productRequest.getType() == Type.OUT){
            if(minus < 0 || reqQuantity > 9999 ){
                throw new BussinesValidationException(List.of("This product have " + product.getQuantity() + " and you quanto to remove " + productRequest.getQuantity()));
            }

           product.setQuantity(product.getQuantity() - reqQuantity);
        }

        productRepository.saveAndFlush(product);

        var userId = request.getAttribute("user_id").toString();

        Long movedBy = Long.parseLong(userId);

        InventoryMovements movement = InventoryMovements.builder()
                .productId(product.getId())
                .product(product)
                .quantity(productRequest.getQuantity())
                .type(productRequest.getType())
                .description(productRequest.getDescription())
                .movedBy(movedBy)
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
