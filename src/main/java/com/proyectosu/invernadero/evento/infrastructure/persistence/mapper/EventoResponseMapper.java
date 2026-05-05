package com.proyectosu.invernadero.rest.mapper;

import com.proyectosu.invernadero.evento.domain.Evento;
import com.proyectosu.invernadero.evento.dto.response.EventoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventoResponseMapper {

    EventoResponse toResponse(Evento evento);

    List<EventoResponse> toResponseList(List<Evento> eventos);
}
