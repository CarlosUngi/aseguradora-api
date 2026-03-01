package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Solicitud para crear cualquier tipo de póliza")
public class PolicyCreateRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;

    @NotNull(message = "El tipo de póliza es obligatorio (1: Vida, 2: Vehículo, 3: Salud)")
    private Integer policyTypeId;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal tarifaTotal;
    private String estado;

    @Schema(description = "Lista de beneficiarios. Obligatorio/Permitido solo para Salud (ID 3)")
    private List<BeneficiaryRequest> beneficiaries;

    @Schema(description = "Lista de vehículos. Permitido solo para Vehículo (ID 2)")
    private List<VehicleRequest> vehicles;

    @Data
    public static class BeneficiaryRequest {
        private String nombres;
        private String apellidos;
        private String parentesco;
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