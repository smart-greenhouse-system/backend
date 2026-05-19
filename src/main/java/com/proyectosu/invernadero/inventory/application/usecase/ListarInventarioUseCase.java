package com.proyectosu.invernadero.inventory.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import com.proyectosu.invernadero.inventory.domain.ports.ItemInventarioRepositoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListarInventarioUseCase {

    private final ItemInventarioRepositoryPort repositoryPort;

    public List<ItemInventario> ejecutar() {
        return repositoryPort.listarTodos();
    }
}