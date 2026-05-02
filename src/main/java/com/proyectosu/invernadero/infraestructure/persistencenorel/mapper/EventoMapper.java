package com.proyectosu.invernadero.infraestructure.persistencenorel.mapper;

import com.proyectosu.invernadero.domain.model.prueba.RegistroEvento;
import com.proyectosu.invernadero.infraestructure.persistencenorel.document.EventoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistroEventoMapper {
    EventoDocument toDocument(RegistroEvento evento);

    RegistroEvento toDomain(EventoDocument document);
}
