package com.proyectosu.invernadero.ia.domain.model;

public enum AiModelType {
    LECHUGA("lechuga"),
    TOMATE("tomate"),
    MONEDA("moneda");

    private final String path;

    AiModelType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static AiModelType from(String value) {
        for (AiModelType model : values()) {
            if (model.name().equalsIgnoreCase(value) || model.path.equalsIgnoreCase(value)) {
                return model;
            }
        }
        throw new IllegalArgumentException("Modelo IA no soportado: " + value);
    }
}
