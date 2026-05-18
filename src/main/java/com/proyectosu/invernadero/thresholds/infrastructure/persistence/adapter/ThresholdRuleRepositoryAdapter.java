package com.proyectosu.invernadero.thresholds.infrastructure.persistence.adapter;

import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;
import com.proyectosu.invernadero.thresholds.domain.ports.ThresholdRuleRepositoryPort;
import com.proyectosu.invernadero.thresholds.infrastructure.persistence.document.ThresholdRuleDocument;
import com.proyectosu.invernadero.thresholds.infrastructure.persistence.repository.ThresholdRuleMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ThresholdRuleRepositoryAdapter implements ThresholdRuleRepositoryPort {

    private final ThresholdRuleMongoRepository repository;

    @Override
    public Optional<ThresholdRule> findByGreenhouseIdAndVariable(String greenhouseId, String variable) {
        return repository.findByGreenhouseIdAndVariable(greenhouseId, variable)
                .map(this::toDomain);
    }

    @Override
    public ThresholdRule save(ThresholdRule thresholdRule) {
        ThresholdRuleDocument document = repository.findByGreenhouseIdAndVariable(
                        thresholdRule.getGreenhouseId(),
                        thresholdRule.getVariable()
                )
                .map(existing -> updateDocument(existing, thresholdRule))
                .orElseGet(() -> toDocument(thresholdRule));

        ThresholdRuleDocument saved = repository.save(document);
        return toDomain(saved);
    }

    private ThresholdRuleDocument updateDocument(ThresholdRuleDocument document, ThresholdRule thresholdRule) {
        document.setId(thresholdRule.getId());
        document.setGreenhouseId(thresholdRule.getGreenhouseId());
        document.setVariable(thresholdRule.getVariable());
        document.setMinValue(thresholdRule.getMinValue());
        document.setMaxValue(thresholdRule.getMaxValue());
        document.setUnit(thresholdRule.getUnit());
        document.setSeverity(thresholdRule.getSeverity());
        document.setEnabled(thresholdRule.isEnabled());
        document.setCreatedAt(thresholdRule.getCreatedAt());
        document.setUpdatedAt(thresholdRule.getUpdatedAt());
        return document;
    }

    private ThresholdRuleDocument toDocument(ThresholdRule thresholdRule) {
        ThresholdRuleDocument document = new ThresholdRuleDocument();
        document.setId(thresholdRule.getId());
        document.setGreenhouseId(thresholdRule.getGreenhouseId());
        document.setVariable(thresholdRule.getVariable());
        document.setMinValue(thresholdRule.getMinValue());
        document.setMaxValue(thresholdRule.getMaxValue());
        document.setUnit(thresholdRule.getUnit());
        document.setSeverity(thresholdRule.getSeverity());
        document.setEnabled(thresholdRule.isEnabled());
        document.setCreatedAt(thresholdRule.getCreatedAt());
        document.setUpdatedAt(thresholdRule.getUpdatedAt());
        return document;
    }

    private ThresholdRule toDomain(ThresholdRuleDocument document) {
        return new ThresholdRule(
                document.getId(),
                document.getGreenhouseId(),
                document.getVariable(),
                document.getMinValue(),
                document.getMaxValue(),
                document.getUnit(),
                document.getSeverity(),
                document.isEnabled(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}