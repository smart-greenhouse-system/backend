package com.proyectosu.invernadero.alerts.domain.ports;

import com.proyectosu.invernadero.alerts.domain.model.Alert;

import java.util.List;

public interface AlertRepositoryPort {

    Alert save(Alert alert);

    List<Alert> findAll();
}