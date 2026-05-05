package com.proyectosu.invernadero.evento.application.usecase;

import com.proyectosu.invernadero.evento.domain.Evento;
import com.proyectosu.invernadero.evento.domain.ports.EventoRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuardarEventoUseCase {

    private final EventoRepositoryPort eventoRepositoryPort;

    public void ejecutar(String origen, String tipo, String mensaje) {

        Evento evento = new Evento(origen, tipo, mensaje);
        eventoRepositoryPort.guardar(evento);

    }
}
