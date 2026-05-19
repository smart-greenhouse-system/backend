package com.proyectosu.invernadero.inventory.application.usecase;

import com.proyectosu.invernadero.inventory.domain.model.ItemInventario;
import com.proyectosu.invernadero.inventory.domain.ports.ItemInventarioRepositoryPort;
import com.proyectosu.invernadero.inventory.dto.request.ItemInventarioUpdateRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ActualizarItemInventarioUseCase {

    private static final String INVALID_INVENTORY_CODE = "INVALID_INVENTORY_ITEM";

    private final ItemInventarioRepositoryPort repositoryPort;

    public ItemInventario ejecutar(String id, ItemInventarioUpdateRequest request) {
        String normalizedId = normalizeRequiredText(id, "id es obligatorio");

        ItemInventario current = repositoryPort.buscarPorId(normalizedId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "INVENTORY_ITEM_NOT_FOUND", "Item no encontrado"));

        if (request.getCantidad() == null && request.getThreshold_minimo() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, INVALID_INVENTORY_CODE, "Debe enviar al menos un campo para actualizar");
        }

        Integer cantidad = request.getCantidad() != null
                ? validateNonNegative(request.getCantidad(), "cantidad")
                : current.getCantidad();

        Integer thresholdMinimo = request.getThreshold_minimo() != null
                ? validateNonNegative(request.getThreshold_minimo(), "threshold_minimo")
                : current.getThresholdMinimo();

        ItemInventario updated = current.with(cantidad, thresholdMinimo, Instant.now());
        return repositoryPort.guardar(updated);
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