package com.proyectosu.invernadero.shared.errores;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
