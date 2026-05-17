package com.proyectosu.invernadero.inventory.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.inventory.domain.model.RegistroConsumo;
import com.proyectosu.invernadero.inventory.infrastructure.persistence.document.RegistroConsumoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistroConsumoMapper {

    RegistroConsumoDocument toDocument(RegistroConsumo domain);

    RegistroConsumo toDomain(RegistroConsumoDocument document);
}