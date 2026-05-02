package com.proyectosu.invernadero.infraestructure.persistencenorel.collections;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventos")

public class EventoDocument {
    @Id
    private String id;

    private String origen;
    private String tipo;
    private String mensaje;
}
