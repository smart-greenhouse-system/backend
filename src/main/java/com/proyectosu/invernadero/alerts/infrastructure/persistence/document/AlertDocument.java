package com.proyectosu.invernadero.alerts.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "alerts")
@Getter
@Setter
public class AlertDocument {

    @Id
    private String id;

    private String greenhouseId;
    private String sensorReadingId;
    private String thresholdRuleId;
    private String source;
    private String description;
    private String severity;
    private BigDecimal value;
    private String notificationStatus;
    private Instant timestamp;

    public AlertDocument() {
    }
}