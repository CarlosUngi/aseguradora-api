package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.RelationshipType;

@Data
@Schema(description = "Solicitud para crear cualquier tipo de póliza")
public class PolicyCreateRequest {

    @Schema(description = "ID del cliente", example = "1")
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;

    @Schema(description = "ID del tipo de póliza 1 vida, 2 vehículo, 3 salud", example = "1")
    @NotNull(message = "El tipo de póliza es obligatorio (1: Vida, 2: Vehículo, 3: Salud)")
    private Integer policyTypeId;

    @Schema(description = "Fecha de inicio de la póliza", example = "2023-01-01")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la póliza", example = "2023-12-31")
    private LocalDate fechaFin; 

    private BigDecimal tarifaTotal;


    @Schema(description = "Lista de beneficiarios. Obligatorio/Permitido solo para Salud (ID 3)")
    private List<BeneficiaryRequest> beneficiaries;

    @Schema(description = "Lista de vehículos. Permitido solo para Vehículo (ID 2)")
    private List<VehicleRequest> vehicles;

    @Data
    public static class BeneficiaryRequest {
        private String nombres;
        private String apellidos;
        @Schema(description = "Parentesco permitido", example = "PADRE ,MADRE ,HIJO ,HIJA ,ESPOSA ,ESPOSO")
        private RelationshipType parentesco;
        private String numeroDocumento;
    }

    @Data
    public static class VehicleRequest {
        private String placa;
        private String marca;
        private String modelo;
        private String anio;
    }
}