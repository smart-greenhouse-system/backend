package com.proyectosu.invernadero.telemetria.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "telemetria_mediciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicionTelemetriaDocument {

    @Id
    private String id;

    @Field("device_id")
    private String deviceId;

    @Field("timestamp")
    private Instant timestamp;

    @Field("temperatura")
    private BigDecimal temperatura;

    @Field("humedad_relativa")
    private BigDecimal humedadRelativa;

    @Field("humedad_suelo")
    private BigDecimal humedadSuelo;

    @Field("iluminacion")
    private BigDecimal iluminacion;

    @Field("co2")
    private BigDecimal co2;
}