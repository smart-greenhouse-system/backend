package com.proyectosu.invernadero.alerts.infrastructure.notification;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertNotificationPort;
import org.springframework.stereotype.Component;

@Component
public class NoOpAlertNotificationAdapter implements AlertNotificationPort {

    @Override
    public void send(Alert alert) {
    }
}