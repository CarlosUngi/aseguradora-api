package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Tag(name = "Gestión de Pólizas", description = "APIs para la gestión de pólizas de seguros")
public class PolicyController {

    private final PolicyServicePort policyServicePort;

    /**
     * Crea una nueva póliza basada en la solicitud proporcionada.
     *
     * @param request La solicitud de creación de la póliza.
     * @return La póliza creada.
     */
    @PostMapping
    @Operation(summary = "Crear una nueva póliza", description = "Crea una nueva póliza para un cliente.")
    public ResponseEntity<Policy> createPolicy(@RequestBody PolicyCreateRequest request) {
        // NOTA: Este es un mapeo simplificado. En una aplicación real, usarías una librería de mapeo como MapStruct.
        Policy policy = new Policy();
        policy.setCustomer(new com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer());
        policy.getCustomer().setId(request.getCustomerId());
        policy.setPolicyType(new com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.PolicyType());
        policy.getPolicyType().setId(request.getPolicyTypeId());
        policy.setFechaInicio(request.getFechaInicio());
        policy.setFechaFin(request.getFechaFin());
        policy.setTarifaTotal(request.getTarifaTotal());
        policy.setEstado(request.getEstado());

        Policy createdPolicy = policyServicePort.createPolicy(policy);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    /**
     * Obtiene una póliza por su ID.
     *
     * @param id El ID de la póliza a obtener.
     * @return La póliza con el ID especificado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una póliza por ID", description = "Obtiene una póliza por su identificador único.")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Policy policy = policyServicePort.getPolicyDetail(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        } else {
            return ResponseEntity.notFound().build();
        }
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
