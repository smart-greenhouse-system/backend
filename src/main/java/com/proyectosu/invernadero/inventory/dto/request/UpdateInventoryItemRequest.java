package com.proyectosu.invernadero.inventory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateInventoryItemRequest {

    private Integer cantidad;

    @JsonProperty("threshold_minimo")
    private Integer thresholdMinimo;
}
