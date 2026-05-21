package com.proyectosu.invernadero.greenhouse.infrastructure.outbound.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "greenhouse_config")
public class MongoGreenhouseConfigDocument {

    @Id
    private String id;

    private String greenhouseName;

    private Boolean automaticMode;

    private Integer aiAnalysisFrequencyMinutes;

    private Instant createdAt;
}
