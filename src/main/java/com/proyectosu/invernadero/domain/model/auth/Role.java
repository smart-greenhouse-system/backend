package com.proyectosu.invernadero.domain.model.auth;

import lombok.Getter;

@Getter
public class Role {

    private Long id;
    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
