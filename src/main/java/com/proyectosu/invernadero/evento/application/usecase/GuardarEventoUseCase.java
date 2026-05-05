package com.proyectosu.invernadero.auth.application.usecase;

import com.proyectosu.invernadero.auth.domain.model.prueba.Evento;
import com.proyectosu.invernadero.auth.domain.ports.EventoRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuardarEventoUseCase {

    private final EventoRepositoryPort eventoRepositoryPort;

    public void ejecutar(String origen, String tipo, String mensaje) {

        Evento evento = new Evento(origen, tipo, mensaje);
        eventoRepositoryPort.guardar(evento);

    }
}
