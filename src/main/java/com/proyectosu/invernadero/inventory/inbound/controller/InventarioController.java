package com.proyectosu.invernadero.inventory.inbound.controller;

import com.proyectosu.invernadero.inventory.application.usecase.RegistrarConsumoUseCase;
import com.proyectosu.invernadero.inventory.domain.model.RegistroConsumo;
import com.proyectosu.invernadero.inventory.dto.request.RegistroConsumoRequest;
import com.proyectosu.invernadero.inventory.dto.response.RegistroConsumoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventarioController {

    private final RegistrarConsumoUseCase registrarConsumoUseCase;

    @PostMapping("/consumption")
    public ResponseEntity<RegistroConsumoResponse> registrarConsumo(
            @Valid @RequestBody RegistroConsumoRequest request
    ) {
        RegistroConsumo registroConsumo = registrarConsumoUseCase.ejecutar(
                request.getItem_id(),
                request.getCrop_id(),
                request.getQuantity(),
                request.getUnit(),
                request.getSource()
        );

        return ResponseEntity.ok(new RegistroConsumoResponse(
                registroConsumo.getId(),
                "Resource consumption registered successfully"
        ));
    }
}