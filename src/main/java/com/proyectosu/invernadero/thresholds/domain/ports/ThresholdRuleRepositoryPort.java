package com.proyectosu.invernadero.thresholds.domain.ports;

import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;

import java.util.Optional;

public interface ThresholdRuleRepositoryPort {

    Optional<ThresholdRule> findByGreenhouseIdAndVariable(String greenhouseId, String variable);

    ThresholdRule save(ThresholdRule thresholdRule);
}