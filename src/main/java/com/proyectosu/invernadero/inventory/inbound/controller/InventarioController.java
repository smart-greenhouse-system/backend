package com.proyectosu.invernadero.inventory.inbound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectosu.invernadero.inventory.application.usecase.ActualizarItemInventarioUseCase;
import com.proyectosu.invernadero.inventory.application.usecase.CrearItemInventarioUseCase;
import com.proyectosu.invernadero.inventory.application.usecase.ListarInventarioUseCase;
import com.proyectosu.invernadero.inventory.dto.request.ItemInventarioCreateRequest;
import com.proyectosu.invernadero.inventory.dto.request.ItemInventarioUpdateRequest;
import com.proyectosu.invernadero.inventory.dto.response.ItemInventarioActionResponse;
import com.proyectosu.invernadero.inventory.dto.response.ItemInventarioResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventarioController {

    private final ListarInventarioUseCase listarInventarioUseCase;
    private final CrearItemInventarioUseCase crearItemInventarioUseCase;
    private final ActualizarItemInventarioUseCase actualizarItemInventarioUseCase;

    @GetMapping
    public ResponseEntity<List<ItemInventarioResponse>> listarInventario() {
        List<ItemInventarioResponse> items = listarInventarioUseCase.ejecutar()
                .stream()
                .map(ItemInventarioResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ItemInventarioActionResponse> crearItem(
            @Valid @RequestBody ItemInventarioCreateRequest request
    ) {
        crearItemInventarioUseCase.ejecutar(request);

        return ResponseEntity.ok(new ItemInventarioActionResponse(
                "Item creado correctamente"
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemInventarioActionResponse> actualizarItem(
            @PathVariable("id") String id,
            @RequestBody ItemInventarioUpdateRequest request
    ) {
        actualizarItemInventarioUseCase.ejecutar(id, request);

        return ResponseEntity.ok(new ItemInventarioActionResponse(
                "Inventario actualizado correctamente"
        ));
    }
}