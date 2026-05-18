package com.proyectosu.invernadero.thresholds.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThresholdRuleRequest {

    @NotBlank(message = "greenhouse_id es obligatorio")
    private String greenhouse_id;

    @NotBlank(message = "variable es obligatoria")
    private String variable;

    @NotNull(message = "min_value es obligatorio")
    private BigDecimal min_value;

    @NotNull(message = "max_value es obligatorio")
    private BigDecimal max_value;

    @NotBlank(message = "unit es obligatoria")
    private String unit;

    @NotBlank(message = "severity es obligatoria")
    private String severity;

    @NotNull(message = "enabled es obligatorio")
    private Boolean enabled;
}