package com.proyectosu.invernadero.alerts.domain.ports;

import com.proyectosu.invernadero.alerts.domain.model.Alert;

public interface AlertNotificationPort {

    void send(Alert alert);
}