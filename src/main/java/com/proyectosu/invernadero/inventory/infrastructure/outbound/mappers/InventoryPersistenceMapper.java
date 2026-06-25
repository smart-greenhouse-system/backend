package com.proyectosu.invernadero.inventory.infrastructure.outbound.mappers;

import com.proyectosu.invernadero.inventory.domain.model.InventoryItem;
import com.proyectosu.invernadero.inventory.infrastructure.outbound.persistence.MongoInventoryItemDocument;
import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface InventoryPersistenceMapper {

    MongoInventoryItemDocument toDocument(InventoryItem inventoryItem);

    default InventoryItem toDomain(MongoInventoryItemDocument document) {
        if (document == null) {
            return null;
        }

        return InventoryItem.restore(
                document.getId(),
                document.getNombre(),
                document.getCantidad(),
                document.getUnidad(),
                document.getThresholdMinimo(),
                document.getCreatedAt() != null ? document.getCreatedAt() : Instant.EPOCH
        );
    }
}
