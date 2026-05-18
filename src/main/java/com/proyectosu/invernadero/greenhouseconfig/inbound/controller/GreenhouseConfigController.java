package com.proyectosu.invernadero.greenhouseconfig.inbound.controller;

import com.proyectosu.invernadero.greenhouseconfig.application.usecase.GetGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.application.usecase.UpdateGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.dto.request.GreenhouseConfigRequest;
import com.proyectosu.invernadero.greenhouseconfig.dto.response.GreenhouseConfigResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class GreenhouseConfigController {

    private final GetGreenhouseConfigUseCase getGreenhouseConfigUseCase;
    private final UpdateGreenhouseConfigUseCase updateGreenhouseConfigUseCase;

    @GetMapping
    public ResponseEntity<GreenhouseConfigResponse> get() {
        GreenhouseConfig config = getGreenhouseConfigUseCase.ejecutar();
        return ResponseEntity.ok(GreenhouseConfigResponse.fromDomain(config));
    }

    @PatchMapping
    public ResponseEntity<GreenhouseConfigResponse> update(@Valid @RequestBody GreenhouseConfigRequest request) {
        GreenhouseConfig config = updateGreenhouseConfigUseCase.ejecutar(request);
        return ResponseEntity.ok(GreenhouseConfigResponse.fromDomain(config));
    }
}