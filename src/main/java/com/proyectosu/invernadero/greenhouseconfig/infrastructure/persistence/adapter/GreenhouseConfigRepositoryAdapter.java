package com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.domain.ports.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.document.GreenhouseConfigDocument;
import com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.repository.GreenhouseConfigMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class GreenhouseConfigRepositoryAdapter implements GreenhouseConfigRepositoryPort {

    private static final String GLOBAL_KEY = "global";

    private final GreenhouseConfigMongoRepository repository;

    @Override
    public GreenhouseConfig read() {
        return repository.findByConfigKey(GLOBAL_KEY)
                .map(this::toDomain)
                .orElseGet(this::defaultConfig);
    }

    @Override
    public GreenhouseConfig save(GreenhouseConfig config) {
        GreenhouseConfigDocument document = repository.findByConfigKey(GLOBAL_KEY)
                .orElseGet(GreenhouseConfigDocument::new);

        document.setConfigKey(GLOBAL_KEY);
        document.setAutomaticMode(config.getAutomaticMode());
        document.setInactivityThresholdMinutes(config.getInactivityThresholdMinutes());
        document.setManualOverrideDurationMinutes(config.getManualOverrideDurationMinutes());
        document.setReportTimezone(config.getReportTimezone());
        document.setUpdatedAt(config.getUpdatedAt());

        return toDomain(repository.save(document));
    }

    private GreenhouseConfig defaultConfig() {
        return new GreenhouseConfig(
                GLOBAL_KEY,
                false,
                10,
                10,
                "UTC",
                Instant.now()
        );
    }

    private GreenhouseConfig toDomain(GreenhouseConfigDocument document) {
        return new GreenhouseConfig(
                document.getConfigKey(),
                document.getAutomaticMode(),
                document.getInactivityThresholdMinutes(),
                document.getManualOverrideDurationMinutes(),
                document.getReportTimezone(),
                document.getUpdatedAt()
        );
    }
}