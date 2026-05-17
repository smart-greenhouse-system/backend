package com.proyectosu.invernadero.inventory.domain.ports;

import com.proyectosu.invernadero.inventory.domain.model.RegistroConsumo;

public interface RegistroConsumoRepositoryPort {

    RegistroConsumo guardar(RegistroConsumo registroConsumo);
}