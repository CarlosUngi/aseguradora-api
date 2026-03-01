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

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "CC", description = "Tipo de documento (CC, CE, NIT)")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Schema(example = "1030662725")
    private String numeroDocumento;

    @NotBlank(message = "Los nombres son obligatorios")
    @Schema(example = "Juan")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Schema(example = "Pérez")
    private String apellidos;

    @Email(message = "El formato del email no es válido")
    @Schema(example = "ejemplo@test.com")
    private String email;

    @Schema(example = "291837")
    private String telefono;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(example = "1990-01-01")
    private LocalDate fechaNacimiento;
}