package com.proyectosu.invernadero.alerts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThresholdAlertResponse {

    private String message;
    private String alert_id;
    private String notification_status;
}