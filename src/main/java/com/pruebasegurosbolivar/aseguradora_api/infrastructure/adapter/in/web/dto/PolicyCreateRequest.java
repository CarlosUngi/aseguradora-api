package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.RelationshipType;

/**
 * DTO para la solicitud de creación de una nueva póliza.
 * Contiene la información necesaria para registrar pólizas de Vida, Vehículo o Salud,
 * incluyendo sus beneficiarios o vehículos asociados según corresponda.
 */
@Data
@Schema(description = "Solicitud para crear cualquier tipo de póliza")
public class PolicyCreateRequest {

    /**
     * Identificador único del cliente titular de la póliza.
     * Es obligatorio.
     */
    @Schema(description = "ID del cliente", example = "1")
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;

    /**
     * Identificador del tipo de póliza a crear.
     * 1: Vida, 2: Vehículo, 3: Salud.
     * Es obligatorio.
     */
    @Schema(description = "ID del tipo de póliza 1 vida, 2 vehículo, 3 salud", example = "1")
    @NotNull(message = "El tipo de póliza es obligatorio (1: Vida, 2: Vehículo, 3: Salud)")
    private Integer policyTypeId;

    /**
     * Fecha de inicio de vigencia de la póliza.
     * Es obligatoria.
     */
    @Schema(description = "Fecha de inicio de la póliza", example = "2023-01-01")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    /**
     * Fecha de fin de vigencia de la póliza.
     */
    @Schema(description = "Fecha de fin de la póliza", example = "2023-12-31")
    private LocalDate fechaFin; 

    /**
     * Costo total de la póliza.
     */
    private BigDecimal tarifaTotal;


    /**
     * Lista de beneficiarios asociados a la póliza.
     * Requerido para pólizas de Salud y opcional para Vida (según reglas de negocio).
     */
    @Schema(description = "Lista de beneficiarios. Obligatorio/Permitido solo para Salud (ID 3)")
    private List<BeneficiaryRequest> beneficiaries;

    /**
     * Lista de vehículos a asegurar.
     * Requerido exclusivamente para pólizas de Vehículo.
     */
    @Schema(description = "Lista de vehículos. Permitido solo para Vehículo (ID 2)")
    private List<VehicleRequest> vehicles;

    /**
     * DTO interno para la información de beneficiarios en la solicitud.
     */
    @Data
    public static class BeneficiaryRequest {
        /** Nombres del beneficiario. */
        private String nombres;
        /** Apellidos del beneficiario. */
        private String apellidos;
        /** Parentesco con el titular (ej. PADRE, HIJO). */
        @Schema(description = "Parentesco permitido", example = "PADRE ,MADRE ,HIJO ,HIJA ,ESPOSA ,ESPOSO")
        private RelationshipType parentesco;
        /** Número de documento de identidad del beneficiario. */
        private String numeroDocumento;
    }

    /**
     * DTO interno para la información de vehículos en la solicitud.
     */
    @Data
    public static class VehicleRequest {
        /** Placa única del vehículo. */
        private String placa;
        /** Marca del vehículo. */
        private String marca;
        /** Modelo o línea del vehículo. */
        private String modelo;
        /** Año del modelo del vehículo. */
        private String anio;
    }
}