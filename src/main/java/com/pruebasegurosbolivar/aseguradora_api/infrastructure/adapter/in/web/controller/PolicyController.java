package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

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

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Tag(name = "Gestión de Pólizas", description = "APIs para la gestión de pólizas de seguros")
public class PolicyController {

    private final PolicyServicePort policyServicePort;
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
                    @ExampleObject(name = "Póliza de Vida - 2 Beneficiarios", summary = "Vida con dos beneficiarios (Cónyuge y Madre)", value = "{ \"customerId\": 1, \"policyTypeId\": 1, \"fechaInicio\": \"2026-03-01\", \"fechaFin\": \"2027-03-01\", \"tarifaTotal\": 150000, \"beneficiaries\": [{ \"nombres\": \"Shirley\", \"apellidos\": \"Perez\", \"parentesco\": \"CONYUGE\", \"numeroDocumento\": \"87654321\" }, { \"nombres\": \"Rosa\", \"apellidos\": \"Arevalo\", \"parentesco\": \"MADRE\", \"numeroDocumento\": \"55443322\" }] }"),
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
        Policy policy = policyServicePort.getPolicyDetail(id);
        return (policy != null)
                ? ResponseEntity.ok(policyMapper.toResponse(policy))
                : ResponseEntity.notFound().build();
    }

    /**
     * Obtiene todas las pólizas de un cliente dado.
     *
     * @param customerId El ID del cliente.
     * @return Una lista de pólizas para el cliente especificado.
     */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Obtener pólizas por ID de cliente", description = "Obtiene todas las pólizas asociadas a un cliente específico.")
    public ResponseEntity<List<Policy>> getPoliciesByCustomerId(@PathVariable Long customerId) {
        List<Policy> policies = policyServicePort.findByPolicyListByClient(customerId);
        return ResponseEntity.ok(policies);
    }
}
