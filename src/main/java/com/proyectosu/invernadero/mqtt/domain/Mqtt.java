package com.proyectosu.invernadero.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Mqtt {
    private String topico;
    private String mensaje;
}
