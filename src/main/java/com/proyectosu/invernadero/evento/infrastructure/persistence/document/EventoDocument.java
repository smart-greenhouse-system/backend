package com.proyectosu.invernadero.evento.infrastructure.persistence.document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventos")
@Getter
@AllArgsConstructor
public class EventoDocument {
    @Id
    private String id;

    private String origen;
    private String tipo;
    private String mensaje;
}
