package com.proyectosu.invernadero.inventory.application.usecase;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import com.proyectosu.invernadero.inventory.domain.ports.ItemInventarioRepositoryPort;
import com.proyectosu.invernadero.inventory.dto.request.ItemInventarioCreateRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CrearItemInventarioUseCase {

    private static final String INVALID_INVENTORY_CODE = "INVALID_INVENTORY_ITEM";

    private final ItemInventarioRepositoryPort repositoryPort;

    public ItemInventario ejecutar(ItemInventarioCreateRequest request) {
        String nombre = normalizeRequiredText(request.getNombre(), "nombre es obligatorio");
        String unidad = normalizeRequiredText(request.getUnidad(), "unidad es obligatoria");
        Integer cantidad = validateNonNegative(request.getCantidad(), "cantidad");
        Integer thresholdMinimo = validateNonNegative(request.getThreshold_minimo(), "threshold_minimo");

        ItemInventario item = new ItemInventario(
                UUID.randomUUID().toString(),
                nombre,
                cantidad,
                unidad,
                thresholdMinimo,
                Instant.now(),
                Instant.now()
        );

        return repositoryPort.guardar(item);
    }

    private String normalizeRequiredText(String value, String errorMessage) {
        if (value == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_INVENTORY_CODE, errorMessage);
        }

        String trimmed = value.trim();
        if (trimmed.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_INVENTORY_CODE, errorMessage);
        }

        return trimmed;
    }

    private Integer validateNonNegative(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_INVENTORY_CODE, fieldName + " debe ser mayor o igual que cero");
        }

        return value;
    }
}