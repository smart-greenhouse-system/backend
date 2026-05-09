package com.proyectosu.invernadero.mqtt.domain.ports;

import com.proyectosu.invernadero.mqtt.domain.ImagenCam;

import java.util.List;

public interface ImagenCamRepositoryPort {

    ImagenCam guardar(ImagenCam domain);

    List<ImagenCam> listar();
}