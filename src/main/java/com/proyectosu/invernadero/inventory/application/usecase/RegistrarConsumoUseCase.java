package com.proyectosu.invernadero.inventory.application.usecase;

import com.proyectosu.invernadero.inventory.domain.model.RegistroConsumo;
import com.proyectosu.invernadero.inventory.domain.ports.RegistroConsumoRepositoryPort;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class RegistrarConsumoUseCase {

    private static final List<String> SOURCES_VALIDOS = List.of("manual", "automatic");

    private final RegistroConsumoRepositoryPort registroConsumoRepositoryPort;

    public RegistrarConsumoUseCase(RegistroConsumoRepositoryPort registroConsumoRepositoryPort) {
        this.registroConsumoRepositoryPort = registroConsumoRepositoryPort;
    }

    public RegistroConsumo ejecutar(
            String itemId,
            String cropId,
            BigDecimal quantity,
            String unit,
            String source
    ) {
        String normalizedItemId = normalizeRequiredValue(itemId, "El item_id es obligatorio");
        String normalizedCropId = normalizeRequiredValue(cropId, "El crop_id es obligatorio");
        String normalizedUnit = normalizeRequiredValue(unit, "La unidad es obligatoria");
        String normalizedSource = normalizeRequiredValue(source, "El source es obligatorio")
                .toLowerCase(Locale.ROOT);

        if (quantity == null || quantity.signum() <= 0) {
            throw new IllegalArgumentException("La quantity debe ser mayor que cero");
        }

        if (!SOURCES_VALIDOS.contains(normalizedSource)) {
            throw new IllegalArgumentException("El source debe ser manual o automatic");
        }

        RegistroConsumo registroConsumo = new RegistroConsumo(
                null,
                normalizedItemId,
                normalizedCropId,
                quantity,
                normalizedUnit,
                normalizedSource,
                Instant.now()
        );

        return registroConsumoRepositoryPort.guardar(registroConsumo);
    }

    private String normalizeRequiredValue(String value, String errorMessage) {
        String normalizedValue = value == null ? "" : value.trim();

        if (normalizedValue.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return normalizedValue;
    }
}