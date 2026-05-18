package com.proyectosu.invernadero.alerts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlertsListResponse {

    private List<AlertItemResponse> alerts;
}