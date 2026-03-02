package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyResponse;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper.PolicyMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adaptador de entrada REST para la gestión de pólizas.
 * Expone los endpoints necesarios para la creación y consulta de pólizas.
 */
@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Tag(name = "Gestión de Pólizas", description = "APIs para la gestión de pólizas de seguros")
public class PolicyController {

    /**
     * Puerto del servicio de pólizas para la lógica de negocio.
     */
    private final PolicyServicePort policyServicePort;
    /**
     * Mapper para convertir entre DTOs y entidades de póliza.
     */
    private final PolicyMapper policyMapper;

    /**
     * Crea una nueva póliza basada en la solicitud proporcionada.
     *
     * @param request La solicitud de creación de la póliza.
     * @return La póliza creada.
     */
    @PostMapping
    @Operation(summary = "Crear nueva póliza", description = "Permite crear pólizas de Vida, Vehículo o Salud.")
    public ResponseEntity<PolicyResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Póliza de Vida - 1 Beneficiario", summary = "Vida con un beneficiario (Hijo)", value = "{ \"customerId\": 1, \"policyTypeId\": 1, \"fechaInicio\": \"2026-03-01\", \"fechaFin\": \"2027-03-01\", \"tarifaTotal\": 100000, \"beneficiaries\": [{ \"nombres\": \"Juan\", \"apellidos\": \"Garzón\", \"parentesco\": \"HIJO\", \"numeroDocumento\": \"12345678\" }] }"),
                    @ExampleObject(name = "Póliza de Vida - 2 Beneficiarios", summary = "Vida con dos beneficiarios (Cónyuge y Madre)", value = "{ \"customerId\": 1, \"policyTypeId\": 1, \"fechaInicio\": \"2026-03-01\", \"fechaFin\": \"2027-03-01\", \"tarifaTotal\": 150000, \"beneficiaries\": [{ \"nombres\": \"Shirley\", \"apellidos\": \"Perez\", \"parentesco\": \"ESPOSA\", \"numeroDocumento\": \"87654321\" }, { \"nombres\": \"Rosa\", \"apellidos\": \"Arevalo\", \"parentesco\": \"MADRE\", \"numeroDocumento\": \"55443322\" }] }"),
                    @ExampleObject(name = "Póliza de Vehículo", summary = "Ejemplo para un vehículo", value = "{ \"customerId\": 1, \"policyTypeId\": 2, \"fechaInicio\": \"2026-03-01\", \"vehicles\": [{ \"placa\": \"KGV123\", \"marca\": \"Toyota\", \"modelo\": \"Prado\", \"anio\": \"2023\" }] }"),
                    @ExampleObject(name = "Póliza de Salud Full", summary = "Salud para cliente, esposa e hijos", value = "{ \"customerId\": 1, \"policyTypeId\": 3, \"beneficiaries\": [{ \"nombres\": \"Saray\", \"apellidos\": \"Garzón\", \"parentesco\": \"HIJO\", \"numeroDocumento\": \"112233\" }] }")
            })) @Valid @RequestBody PolicyCreateRequest request) {
        Policy policyDomain = policyMapper.toDomain(request);
        Policy savedPolicy = policyServicePort.createPolicy(policyDomain);
        return ResponseEntity.ok(policyMapper.toResponse(savedPolicy));
    }

    /**
     * Obtiene una póliza por su ID.
     *
     * @param id El ID de la póliza a obtener.
     * @return La póliza con el ID especificado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una póliza por ID", description = "Obtiene una póliza por su identificador único.")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id) {
        // Si no existe, getPolicyDetail lanzará BusinessException
        Policy policy = policyServicePort.getPolicyDetail(id);
        return ResponseEntity.ok(policyMapper.toResponse(policy));
    }

    /**
     * Obtiene todas las pólizas de un cliente dado.
     *
     * @param customerId El ID del cliente.
     * @return Una lista de pólizas para el cliente especificado.
     */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Obtener pólizas por ID de cliente", description = "Obtiene todas las pólizas asociadas a un cliente específico.")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByCustomerId(@PathVariable Long customerId) {
        List<Policy> policies = policyServicePort.findByPolicyListByClient(customerId);
        if (policies.isEmpty()) {
            return ResponseEntity.ok(java.util.Collections.emptyList());
        }
        return ResponseEntity.ok(policyMapper.toResponseList(policies));
    }

    @Operation(summary = "Listar beneficiarios por póliza de salud", description = "Retorna la lista de personas cubiertas por una póliza de salud específica.")
    @GetMapping("/{policyId}/beneficiaries")
    public ResponseEntity<List<PolicyResponse.BeneficiaryResponse>> getBeneficiariesByHealthPolicy(
            @PathVariable Long policyId) {
        List<Beneficiary> beneficiaries = policyServicePort.findBeneficiaryByPolicyId(policyId);

        // Utilizamos el mapper para transformar la entidad de dominio al DTO de
        // respuesta
        return ResponseEntity.ok(policyMapper.toBeneficiaryResponseList(beneficiaries));
    }
}
