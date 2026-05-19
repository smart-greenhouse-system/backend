package com.proyectosu.invernadero.inventory.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.document.ItemInventarioDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemInventarioMapper {

    ItemInventarioDocument toDocument(ItemInventario domain);

    ItemInventario toDomain(ItemInventarioDocument document);
}