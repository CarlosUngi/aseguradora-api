package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO para la actualización de un cliente existente.
 * Contiene los campos que pueden ser modificados en la operación de actualización.
 */
@Data
@Schema(description = "Modelo para la actualización de clientes")
public class CustomerUpdateRequest {

    /**
     * Tipo de documento de identidad (ej. CC, TI, CE).
     */
    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "CC", description = "Tipo de documento (CC, CE, NIT)")
    private String tipoDocumento;

    /**
     * Número único del documento de identidad.
     */
    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "1030662725")
    private String numeroDocumento;

    /**
     * Nombres del cliente.
     */
    private String nombres;

    /**
     * Apellidos del cliente.
     */
    private String apellidos;

    /**
     * Correo electrónico de contacto.
     */
    private String email;

    /**
     * Número de teléfono de contacto.
     */
    private String telefono;

    /**
     * Fecha de nacimiento del cliente.
     */
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
}