package com.proyectosu.invernadero.devices.infrastructure.persistence.mapper;

import com.proyectosu.invernadero.devices.domain.model.Dispositivo;
import com.proyectosu.invernadero.devices.infrastructure.persistence.document.DispositivoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DispositivoMapper {

    Dispositivo toDomain(DispositivoDocument document);

    DispositivoDocument toDocument(Dispositivo domain);
}