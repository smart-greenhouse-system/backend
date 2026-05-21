package com.proyectosu.invernadero.inventory.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateInventoryItemCommand {

    private final String nombre;
    private final Integer cantidad;
    private final String unidad;
    private final Integer thresholdMinimo;
}
