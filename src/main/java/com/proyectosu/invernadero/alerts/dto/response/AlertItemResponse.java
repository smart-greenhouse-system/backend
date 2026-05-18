package com.proyectosu.invernadero.alerts.dto.response;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlertItemResponse {

    private String alert_id;
    private String source;
    private String description;
    private String severity;
    private String timestamp;

    public static AlertItemResponse fromDomain(Alert alert) {
        return new AlertItemResponse(
                alert.getId(),
                alert.getSource(),
                alert.getDescription(),
                alert.getSeverity(),
                alert.getTimestamp().toString()
        );
    }
}