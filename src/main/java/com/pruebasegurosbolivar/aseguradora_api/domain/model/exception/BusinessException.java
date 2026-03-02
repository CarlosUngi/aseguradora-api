package com.pruebasegurosbolivar.aseguradora_api.domain.model.exception;

/**
 * Excepción base para errores de lógica de negocio en el sistema de seguros.
 */
public class BusinessException extends RuntimeException {
    /**
     * Construye una nueva BusinessException con el mensaje de detalle especificado.
     * @param message el mensaje de detalle.
     */
    public BusinessException(String message) {
        super(message);
    }
}