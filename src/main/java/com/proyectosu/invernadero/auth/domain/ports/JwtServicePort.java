package com.proyectosu.invernadero.auth.domain.ports;

import com.proyectosu.invernadero.auth.domain.model.User;

import java.util.List;

public interface JwtServicePort {
    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token);

    List<String> extractRoles(String token);

    long getExpirationInSeconds();
}
