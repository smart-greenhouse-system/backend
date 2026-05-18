package com.proyectosu.invernadero.greenhouseconfig.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GreenhouseConfigRequest {

    private Boolean automatic_mode;

    @Min(value = 1, message = "inactivity_threshold_minutes debe ser mayor que cero")
    private Integer inactivity_threshold_minutes;

    @Min(value = 1, message = "manual_override_duration_minutes debe ser mayor que cero")
    private Integer manual_override_duration_minutes;

    private String report_timezone;
}