package com.proyectosu.invernadero.inventory.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateInventoryItemCommand {

    private final String id;
    private final Integer cantidad;
    private final Integer thresholdMinimo;
}
