package com.proyectosu.invernadero.greenhouseconfig.infrastructure.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "greenhouse_config")
@Getter
@Setter
public class GreenhouseConfigDocument {

    @Id
    private String configKey;

    private Boolean automaticMode;
    private Integer inactivityThresholdMinutes;
    private Integer manualOverrideDurationMinutes;
    private String reportTimezone;
    private Instant updatedAt;

    public GreenhouseConfigDocument() {
    }
}