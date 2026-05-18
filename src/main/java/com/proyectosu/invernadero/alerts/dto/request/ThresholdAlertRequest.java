package com.proyectosu.invernadero.alerts.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThresholdAlertRequest {

    @NotBlank(message = "greenhouse_id es obligatorio")
    private String greenhouse_id;

    @NotBlank(message = "sensor_reading_id es obligatorio")
    private String sensor_reading_id;

    @NotBlank(message = "threshold_rule_id es obligatorio")
    private String threshold_rule_id;

    @NotBlank(message = "variable es obligatoria")
    private String variable;

    @NotNull(message = "value es obligatorio")
    private BigDecimal value;

    @NotBlank(message = "severity es obligatoria")
    private String severity;
}