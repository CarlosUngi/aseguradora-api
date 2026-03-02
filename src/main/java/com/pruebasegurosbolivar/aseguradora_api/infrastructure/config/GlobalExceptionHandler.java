package com.pruebasegurosbolivar.aseguradora_api.infrastructure.config;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador de excepciones global para la aplicación.
 * Captura excepciones y las formatea en una respuesta JSON estándar.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructor por defecto para la clase GlobalExceptionHandler.
     */
    public GlobalExceptionHandler() {
        // Constructor por defecto
    }

    /**
     * Maneja las excepciones de negocio (BusinessException).
     * @param ex La excepción de negocio capturada.
     * @return Una respuesta con el código de error y el mensaje.
     */
    // Manejo de tu error de negocio (Cédula duplicada, etc.)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("code", "BUSINESS_ERROR");
        response.put("message", ex.getMessage());
        response.put("originalError", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones genéricas de tipo RuntimeException.
     * @param ex La excepción de runtime capturada.
     * @return Una respuesta genérica de error interno del servidor.
     */
    // En lugar de Exception.class, usa RuntimeException para mayor estabilidad con Swagger
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("code", "INTERNAL_SERVER_ERROR");
        response.put("message", "Ocurrió un error inesperado en el servidor");
        response.put("originalError", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}