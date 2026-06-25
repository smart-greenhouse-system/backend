package com.proyectosu.invernadero.shared.errores;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
