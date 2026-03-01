package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PolicyResponse {
    private Long id;
    private Long customerId;
    private String customerName; 
    private Integer policyTypeId;
    private String policyTypeName;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal tarifaTotal;
    private String estado;
    private List<BeneficiaryResponse> beneficiaries;
    private List<VehicleResponse> vehicles;

    @Data
    public static class BeneficiaryResponse {
        private Long id;
        private String nombres;
        private String apellidos;
        private String parentesco;
        private String numeroDocumento;
    }

    @Data
    public static class VehicleResponse {
        private Long id;
        private String placa;
        private String marca;
        private String modelo;
        private String anio;
    }
}