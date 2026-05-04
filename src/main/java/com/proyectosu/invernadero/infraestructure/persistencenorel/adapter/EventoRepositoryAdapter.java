package com.proyectosu.invernadero.infraestructure.persistencenorel.adapter;

import com.proyectosu.invernadero.domain.model.prueba.Evento;
import com.proyectosu.invernadero.domain.ports.EventoRepositoryPort;
import com.proyectosu.invernadero.infraestructure.persistencenorel.document.EventoDocument;
import com.proyectosu.invernadero.infraestructure.persistencenorel.mapper.EventoMapper;
import com.proyectosu.invernadero.infraestructure.persistencenorel.repository.EventoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventoRepositoryAdapter implements EventoRepositoryPort {

    private final EventoMongoRepository repository;
    private final EventoMapper mapper;

    @Override
    public void guardar(Evento evento) {
        EventoDocument document = mapper.toDocument(evento);
        repository.save(document);
    }

    @Override
    public List<Evento> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
