package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.PolicyType;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.lang.foreign.Linker.Option;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @Operation(summary = "Crear nueva póliza", description = "Permite crear pólizas de Vida, Vehículo o Salud.")
    public ResponseEntity<Policy> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Póliza de Vida - 1 Beneficiario", summary = "Vida con un beneficiario (Hijo)", value = "{ \"customerId\": 1, \"policyTypeId\": 1, \"fechaInicio\": \"2026-03-01\", \"fechaFin\": \"2027-03-01\", \"tarifaTotal\": 100000, \"beneficiaries\": [{ \"nombres\": \"Juan\", \"apellidos\": \"Garzón\", \"parentesco\": \"HIJO\", \"numeroDocumento\": \"12345678\" }] }"),
                    @ExampleObject(name = "Póliza de Vida - 2 Beneficiarios", summary = "Vida con dos beneficiarios (Cónyuge y Madre)", value = "{ \"customerId\": 1, \"policyTypeId\": 1, \"fechaInicio\": \"2026-03-01\", \"fechaFin\": \"2027-03-01\", \"tarifaTotal\": 150000, \"beneficiaries\": [{ \"nombres\": \"Shirley\", \"apellidos\": \"Perez\", \"parentesco\": \"CONYUGE\", \"numeroDocumento\": \"87654321\" }, { \"nombres\": \"Rosa\", \"apellidos\": \"Arevalo\", \"parentesco\": \"MADRE\", \"numeroDocumento\": \"55443322\" }] }"),
                    @ExampleObject(name = "Póliza de Vehículo", summary = "Ejemplo para un vehículo", value = "{ \"customerId\": 1, \"policyTypeId\": 2, \"fechaInicio\": \"2026-03-01\", \"vehicles\": [{ \"placa\": \"KGV123\", \"marca\": \"Toyota\", \"modelo\": \"Prado\", \"anio\": \"2023\" }] }"),
                    @ExampleObject(name = "Póliza de Salud Full", summary = "Salud para cliente, esposa e hijos", value = "{ \"customerId\": 1, \"policyTypeId\": 3, \"beneficiaries\": [{ \"nombres\": \"Saray\", \"apellidos\": \"Garzón\", \"parentesco\": \"HIJO\", \"numeroDocumento\": \"112233\" }] }")
            })) @Valid @RequestBody PolicyCreateRequest request) {
        // NOTA: Este es un mapeo simplificado. En una aplicación real, usarías una
        // librería de mapeo como MapStruct.
        Policy policy = new Policy();
        policy.setCustomer(new Customer());
        policy.getCustomer().setId(request.getCustomerId());
        policy.setPolicyType(new PolicyType());
        policy.getPolicyType().setId(request.getPolicyTypeId());
        policy.setFechaInicio(request.getFechaInicio());
        policy.setFechaFin(request.getFechaFin());
        policy.setTarifaTotal(request.getTarifaTotal());
        policy.setEstado("activo");

        if (!Optional.ofNullable(request.getBeneficiaries()).filter(b -> !b.isEmpty()).isEmpty()) {
            policy.setBeneficiaries(request.getBeneficiaries().stream().map(b -> {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setNombres(b.getNombres());
                beneficiary.setApellidos(b.getApellidos());
                beneficiary.setParentesco(b.getParentesco());
                beneficiary.setNumeroDocumento(b.getNumeroDocumento());
                return beneficiary;
            }).collect(Collectors.toList()));
        }
        if (!Optional.ofNullable(request.getVehicles()).filter(v -> !v.isEmpty()).isEmpty()) {
            policy.setVehicles(request.getVehicles().stream().map(v -> {
                Vehicle vehicle = new Vehicle();
                vehicle.setPlaca(v.getPlaca());
                vehicle.setMarca(v.getMarca());
                vehicle.setModelo(v.getModelo());
                vehicle.setAnio(v.getAnio());
                return vehicle;
            }).collect(Collectors.toList()));
        }

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
