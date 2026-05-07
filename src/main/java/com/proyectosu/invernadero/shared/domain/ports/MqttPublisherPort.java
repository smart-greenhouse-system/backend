package com.proyectosu.invernadero.shared.domain.ports;

public interface MqttPublisherPort {

    void publicar(String topic, Object payload);
}
