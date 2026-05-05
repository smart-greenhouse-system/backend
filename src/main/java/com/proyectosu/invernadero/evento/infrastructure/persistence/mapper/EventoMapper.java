package com.proyectosu.invernadero.auth.infraestructure.persistencenorel.mapper;

import com.proyectosu.invernadero.evento.domain.Evento;
import com.proyectosu.invernadero.evento.infrastructure.persistence.document.EventoDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    EventoDocument toDocument(Evento evento);

    Evento toDomain(EventoDocument document);
}
