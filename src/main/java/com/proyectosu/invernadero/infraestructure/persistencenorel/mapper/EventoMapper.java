package com.proyectosu.invernadero.infraestructure.persistencenorel.mapper;

import com.proyectosu.invernadero.domain.model.prueba.Evento;
import com.proyectosu.invernadero.infraestructure.persistencenorel.document.EventoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    EventoDocument toDocument(Evento evento);

    Evento toDomain(EventoDocument document);
}
