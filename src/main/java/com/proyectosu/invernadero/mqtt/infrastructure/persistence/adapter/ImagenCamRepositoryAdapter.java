package com.proyectosu.invernadero.mqtt.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.mqtt.domain.ImagenCam;
import com.proyectosu.invernadero.mqtt.domain.ports.ImagenCamRepositoryPort;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.mapper.ImagenCamMapper;
import com.proyectosu.invernadero.mqtt.infrastructure.persistence.repository.ImagenCamMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImagenCamRepositoryAdapter implements ImagenCamRepositoryPort {

    private final ImagenCamMongoRepository repository;
    private final ImagenCamMapper mapper;

    @Override
    public ImagenCam guardar(ImagenCam domain) {
        return mapper.toDomain(repository.save(mapper.toDocument(domain)));
    }

    @Override
    public List<ImagenCam> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}