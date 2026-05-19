package com.proyectosu.invernadero.inventory.domain.ports;

import java.util.List;
import java.util.Optional;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;

public interface ItemInventarioRepositoryPort {

    List<ItemInventario> listarTodos();

    Optional<ItemInventario> buscarPorId(String id);

    ItemInventario guardar(ItemInventario itemInventario);
}