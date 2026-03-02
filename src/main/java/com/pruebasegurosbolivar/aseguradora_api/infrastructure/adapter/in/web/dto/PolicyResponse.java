package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO que representa la respuesta con los detalles de una póliza.
 * Estructura la información que se envía al cliente, incluyendo datos planos
 * del cliente y tipo de póliza, así como listas de beneficiarios o vehículos.
 */
@Data
public class PolicyResponse {
    /** Identificador único de la póliza. */
    private Long id;
    /** Identificador del cliente titular. */
    private Long customerId;
    /** Nombre del cliente titular. */
    private String customerName; 
    /** Identificador del tipo de póliza. */
    private Integer policyTypeId;
    /** Nombre descriptivo del tipo de póliza. */
    private String policyTypeName;
    /** Fecha de inicio de la vigencia. */
    private LocalDate fechaInicio;
    /** Fecha de fin de la vigencia. */
    private LocalDate fechaFin;
    /** Valor total de la tarifa de la póliza. */
    private BigDecimal tarifaTotal;
    /** Estado actual de la póliza (ej. Activa). */
    private String estado;
    /** Lista de beneficiarios asociados, si aplica. */
    private List<BeneficiaryResponse> beneficiaries;
    /** Lista de vehículos asegurados, si aplica. */
    private List<VehicleResponse> vehicles;

    /**
     * DTO interno para la respuesta de detalles de beneficiarios.
     */
    @Data
    public static class BeneficiaryResponse {
        /** ID del beneficiario. */
        private Long id;
        /** Nombres del beneficiario. */
        private String nombres;
        /** Apellidos del beneficiario. */
        private String apellidos;
        /** Parentesco con el titular. */
        private String parentesco;
        /** Número de documento de identidad. */
        private String numeroDocumento;
    }

    /**
     * DTO interno para la respuesta de detalles de vehículos.
     */
    @Data
    public static class VehicleResponse {
        /** ID del vehículo. */
        private Long id;
        /** Placa del vehículo. */
        private String placa;
        /** Marca del vehículo. */
        private String marca;
        /** Modelo o línea. */
        private String modelo;
        /** Año del modelo. */
        private String anio;
    }
}