package com.proyectosu.invernadero.devices.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.devices.domain.model.Dispositivo;
import com.proyectosu.invernadero.devices.domain.ports.DispositivoRepositoryPort;
import com.proyectosu.invernadero.devices.infrastructure.persistence.mapper.DispositivoMapper;
import com.proyectosu.invernadero.devices.infrastructure.persistence.repository.DispositivoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DispositivoRepositoryAdapter implements DispositivoRepositoryPort {

    private final DispositivoMongoRepository repository;
    private final DispositivoMapper mapper;

    @Override
    public List<Dispositivo> listarTodos() {
        return repository.findAllByOrderByDeviceIdAsc()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Dispositivo> obtenerPorDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId)
                .map(mapper::toDomain);
    }

    @Override
    public Dispositivo guardar(Dispositivo dispositivo) {
        return mapper.toDomain(repository.save(mapper.toDocument(dispositivo)));
    }
}