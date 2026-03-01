package com.pruebasegurosbolivar.aseguradora_api.domain.model.exception;

/**
 * Excepción base para errores de lógica de negocio en el sistema de seguros.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}