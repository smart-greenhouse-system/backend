package com.proyectosu.invernadero.shared.errores;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwt() {
        return build(HttpStatus.UNAUTHORIZED, "Token expirado");
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwt() {
        return build(HttpStatus.UNAUTHORIZED, "token invalido");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral() {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error de servidor");
    }

    private ResponseEntity<Map<String,Object>> build(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity.status(status).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message
                )
        );
    }
}
