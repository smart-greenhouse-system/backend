package com.proyectosu.invernadero.inventory.dto.response;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemInventarioResponse {

    private final String id;
    private final String nombre;
    private final Integer cantidad;
    private final String unidad;
    private final Integer threshold_minimo;

    public static ItemInventarioResponse fromDomain(ItemInventario itemInventario) {
        return new ItemInventarioResponse(
                itemInventario.getId(),
                itemInventario.getNombre(),
                itemInventario.getCantidad(),
                itemInventario.getUnidad(),
                itemInventario.getThresholdMinimo()
        );
    }
}