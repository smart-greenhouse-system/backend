package com.proyectosu.invernadero.shared.errores;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApi(ApiException ex) {
        return build(ex.getStatus(), ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("Solicitud invalida");

        return build(HttpStatus.BAD_REQUEST, message);
    }

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
        return build(status, null, message);
        }

        private ResponseEntity<Map<String,Object>> build(
            HttpStatus status,
            String code,
            String message
        ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        if (code != null) {
            body.put("code", code);
        }
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}
