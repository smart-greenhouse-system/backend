package com.proyectosu.invernadero.greenhouseconfig.dto.response;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GreenhouseConfigResponse {

    private Boolean automatic_mode;
    private Integer inactivity_threshold_minutes;
    private Integer manual_override_duration_minutes;
    private String report_timezone;
    private String updated_at;

    public static GreenhouseConfigResponse fromDomain(GreenhouseConfig config) {
        return new GreenhouseConfigResponse(
                config.getAutomaticMode(),
                config.getInactivityThresholdMinutes(),
                config.getManualOverrideDurationMinutes(),
                config.getReportTimezone(),
                config.getUpdatedAt() == null ? null : config.getUpdatedAt().toString()
        );
    }
}