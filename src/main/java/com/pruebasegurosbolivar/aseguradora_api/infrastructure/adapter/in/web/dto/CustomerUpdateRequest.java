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
@Schema(description = "Modelo para la actualización de clientes")
public class CustomerUpdateRequest {

    @NotNull(message = "El ID es obligatorio")
    @Schema(example = "1")
    private Long id;


    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "CC", description = "Tipo de documento (CC, CE, NIT)")
    private String tipoDocumento;


    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(example = "1030662725")
    private String numeroDocumento;

    private String nombres;

    private String apellidos;

    private String email;

    private String telefono;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
}