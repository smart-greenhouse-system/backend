package com.proyectosu.invernadero.alerts.inbound.controller;

import com.proyectosu.invernadero.alerts.application.usecase.CreateThresholdAlertUseCase;
import com.proyectosu.invernadero.alerts.application.usecase.ListAlertsUseCase;
import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.dto.request.ThresholdAlertRequest;
import com.proyectosu.invernadero.alerts.dto.response.AlertItemResponse;
import com.proyectosu.invernadero.alerts.dto.response.AlertsListResponse;
import com.proyectosu.invernadero.alerts.dto.response.ThresholdAlertResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertsController {

    private final CreateThresholdAlertUseCase createThresholdAlertUseCase;
    private final ListAlertsUseCase listAlertsUseCase;

    @GetMapping
    public ResponseEntity<AlertsListResponse> list(
            @RequestParam(value = "greenhouse_id", required = false) String greenhouseId,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        List<Alert> alerts = listAlertsUseCase.ejecutar(greenhouseId, from, to);
        List<AlertItemResponse> responseAlerts = alerts.stream()
                .map(AlertItemResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(new AlertsListResponse(responseAlerts));
    }

    @PostMapping("/threshold")
    public ResponseEntity<ThresholdAlertResponse> createThresholdAlert(
            @Valid @RequestBody ThresholdAlertRequest request
    ) {
        Alert alert = createThresholdAlertUseCase.ejecutar(request);

        return ResponseEntity.ok(new ThresholdAlertResponse(
                "Threshold alert generated successfully",
                alert.getId(),
                alert.getNotificationStatus()
        ));
    }
}