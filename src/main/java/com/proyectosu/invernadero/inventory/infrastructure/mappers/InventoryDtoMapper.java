package com.proyectosu.invernadero.inventory.infrastructure.mappers;

import com.proyectosu.invernadero.inventory.application.command.CreateInventoryItemCommand;
import com.proyectosu.invernadero.inventory.application.command.UpdateInventoryItemCommand;
import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.dto.request.CreateInventoryItemRequest;
import com.proyectosu.invernadero.inventory.dto.request.UpdateInventoryItemRequest;
import com.proyectosu.invernadero.inventory.dto.response.InventoryItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryDtoMapper {

    CreateInventoryItemCommand toCreateCommand(CreateInventoryItemRequest request);

    @Mapping(target = "id", source = "id")
    UpdateInventoryItemCommand toUpdateCommand(String id, UpdateInventoryItemRequest request);

    InventoryItemResponse toResponse(InventoryItem inventoryItem);

    List<InventoryItemResponse> toResponseList(List<InventoryItem> inventoryItems);
}
