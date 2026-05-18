package com.proyectosu.invernadero.alerts.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.alerts.infrastructure.persistence.document.AlertDocument;
import com.proyectosu.invernadero.alerts.infrastructure.persistence.repository.AlertMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertRepositoryAdapter implements AlertRepositoryPort {

    private final AlertMongoRepository repository;

    @Override
    public Alert save(Alert alert) {
        AlertDocument saved = repository.save(toDocument(alert));
        return toDomain(saved);
    }

    @Override
    public List<Alert> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private AlertDocument toDocument(Alert alert) {
        AlertDocument document = new AlertDocument();
        document.setId(alert.getId());
        document.setGreenhouseId(alert.getGreenhouseId());
        document.setSensorReadingId(alert.getSensorReadingId());
        document.setThresholdRuleId(alert.getThresholdRuleId());
        document.setSource(alert.getSource());
        document.setDescription(alert.getDescription());
        document.setSeverity(alert.getSeverity());
        document.setValue(alert.getValue());
        document.setNotificationStatus(alert.getNotificationStatus());
        document.setTimestamp(alert.getTimestamp());
        return document;
    }

    private Alert toDomain(AlertDocument document) {
        return new Alert(
                document.getId(),
                document.getGreenhouseId(),
                document.getSensorReadingId(),
                document.getThresholdRuleId(),
                document.getSource(),
                document.getDescription(),
                document.getSeverity(),
                document.getValue(),
                document.getNotificationStatus(),
                document.getTimestamp()
        );
    }
}