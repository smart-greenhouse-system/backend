package com.proyectosu.invernadero.thresholds.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "threshold_rules")
@Getter
@Setter
public class ThresholdRuleDocument {

    @Id
    private String id;

    private String greenhouseId;
    private String variable;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private String unit;
    private String severity;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;

    public ThresholdRuleDocument() {
    }
}