package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO para la creación de un nuevo cliente.
 * Separa la capa de presentación del modelo de dominio.
 */
@Data
@Schema(description = "Modelo para la creación de clientes")
public class CustomerCreateRequest {

    /**
     * Tipo de documento de identidad (ej. CC, TI, CE).
     * Es obligatorio.
     */
    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "CC", description = "Tipo de documento (CC, CE, NIT)")
    private String tipoDocumento;

    /**
     * Número único del documento de identidad.
     * Es obligatorio.
     */
    @NotBlank(message = "El número de documento es obligatorio")
    @Schema(example = "1030662725")
    private String numeroDocumento;

    /**
     * Nombres del cliente.
     * Es obligatorio.
     */
    @NotBlank(message = "Los nombres son obligatorios")
    @Schema(example = "Juan")
    private String nombres;

    /**
     * Apellidos del cliente.
     * Es obligatorio.
     */
    @NotBlank(message = "Los apellidos son obligatorios")
    @Schema(example = "Pérez")
    private String apellidos;

    /**
     * Correo electrónico de contacto.
     * Debe tener un formato válido.
     */
    @Email(message = "El formato del email no es válido")
    @Schema(example = "ejemplo@test.com")
    private String email;

    /**
     * Número de teléfono de contacto.
     */
    @Schema(example = "291837")
    private String telefono;

    /**
     * Fecha de nacimiento del cliente.
     * Debe ser una fecha pasada.
     */
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(example = "1990-01-01")
    private LocalDate fechaNacimiento;
}