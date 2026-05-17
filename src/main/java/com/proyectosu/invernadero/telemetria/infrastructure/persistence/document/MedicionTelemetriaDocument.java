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
import java.util.Map;

@Document(collection = "sensor_data")
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

    @Field("sensores")
    private Map<String, BigDecimal> sensores;
}