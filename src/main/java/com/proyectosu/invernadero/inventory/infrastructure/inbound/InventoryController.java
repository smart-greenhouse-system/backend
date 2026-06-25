package com.proyectosu.invernadero.inventory.infrastructure.inbound;

import com.proyectosu.invernadero.inventory.application.usecases.CreateInventoryItemUseCase;
import com.proyectosu.invernadero.inventory.application.usecases.GetInventoryUseCase;
import com.proyectosu.invernadero.inventory.application.usecases.UpdateInventoryItemUseCase;
import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.dto.request.CreateInventoryItemRequest;
import com.proyectosu.invernadero.inventory.dto.request.UpdateInventoryItemRequest;
import com.proyectosu.invernadero.inventory.dto.response.InventoryItemResponse;
import com.proyectosu.invernadero.inventory.infrastructure.mappers.InventoryDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final GetInventoryUseCase getInventoryUseCase;
    private final CreateInventoryItemUseCase createInventoryItemUseCase;
    private final UpdateInventoryItemUseCase updateInventoryItemUseCase;
    private final InventoryDtoMapper inventoryDtoMapper;

    @GetMapping
    public ResponseEntity<List<InventoryItemResponse>> getInventory() {
        List<InventoryItem> inventory = getInventoryUseCase.execute();

        return ResponseEntity.ok(inventoryDtoMapper.toResponseList(inventory));
    }

    @PostMapping
    public ResponseEntity<InventoryItemResponse> createInventoryItem(
            @RequestBody CreateInventoryItemRequest request
    ) {
        InventoryItem createdItem = createInventoryItemUseCase.execute(
                inventoryDtoMapper.toCreateCommand(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inventoryDtoMapper.toResponse(createdItem));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> updateInventoryItem(
            @PathVariable String id,
            @RequestBody UpdateInventoryItemRequest request
    ) {
        InventoryItem updatedItem = updateInventoryItemUseCase.execute(
                inventoryDtoMapper.toUpdateCommand(id, request)
        );

        return ResponseEntity.ok(inventoryDtoMapper.toResponse(updatedItem));
    }
}
