package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.RelationshipType;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.BeneficiaryRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.VehicleRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de los casos de uso para pólizas.
 * Esta clase actúa como el orquestador entre los puertos de entrada y salida.
 */
@Service
@RequiredArgsConstructor
public class PolicyUseCase implements PolicyServicePort {

    private final PolicyRepositoryPort policyRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final BeneficiaryRepositoryPort beneficiaryRepositoryPort;
    private final VehicleRepositoryPort vehicleRepositoryPort;

    /**
     * {@inheritDoc}
     * Implementa las reglas de negocio por tipo de póliza:
     * - Salud (3): Permite beneficiarios, prohíbe vehículos.
     * - Vehículo (2): Permite vehículos, prohíbe beneficiarios.
     * - Vida (1): Prohíbe ambos.
     */
    @Override
    public Policy createPolicy(Policy policy) {
        int typeId = policy.getPolicyType().getId();
        Long customerId = policy.getCustomer().getId();

        customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + customerId));

        switch (typeId) {
            case 1: // VIDA
                validateVida(policy, customerId);
                break;
            case 2: // VEHÍCULO
                validateVehiculo(policy);
                break;
            case 3: // SALUD
                validateSalud(policy);
                break;
            default:
                throw new BusinessException("Tipo de póliza no reconocido.");
        }
        if (policy.getPolicyType().getId() == 2) { // VEHÍCULO
            validateVehiculo(policy);

            // Lógica para evitar duplicados:
            List<Vehicle> processedVehicles = policy.getVehicles().stream()
                    .map(v -> vehicleRepositoryPort.findByPlaca(v.getPlaca())
                            .orElse(v)) // Si existe en BD lo usamos, si no, usamos el nuevo
                    .collect(Collectors.toList());

            policy.setVehicles(processedVehicles);
        }
        return policyRepositoryPort.save(policy);
    }

    private void validateVida(Policy policy, Long customerId) {
        List<Policy> currentPolicies = policyRepositoryPort.findByCustomerId(customerId);
        boolean hasLifePolicy = currentPolicies.stream()
                .anyMatch(p -> p.getPolicyType().getId() == 1);
        if (hasLifePolicy)
            throw new BusinessException("Ya existe una póliza de vida activa para este cliente.");

        if (policy.getBeneficiaries().size() > 2)
            throw new BusinessException("Una Poliza de vida solo puede tener maximo 2 beneficiarios");

        Optional.ofNullable(policy.getVehicles())
                .filter(v -> !v.isEmpty())
                .ifPresent(v -> {
                    throw new BusinessException("La póliza de Vida no permite vehículos.");
                });
    }

    private void validateVehiculo(Policy policy) {
        Optional.ofNullable(policy.getBeneficiaries())
                .filter(b -> !b.isEmpty())
                .ifPresent(b -> {
                    throw new BusinessException("La póliza de Vehículo no permite el registro de beneficiarios.");
                });
        Optional.ofNullable(policy.getVehicles())
                .filter(list -> !list.isEmpty())
                .orElseThrow(
                        () -> new BusinessException("Debe incluir al menos un vehículo para este tipo de póliza."));

        if (policy.getVehicles().stream().map(v -> v.getPlaca()).distinct().count() < policy.getVehicles().size())
            throw new BusinessException("hay placas repetidas dentro de sus vehiculos");
    }

    private void validateSalud(Policy policy) {
        Optional.ofNullable(policy.getVehicles())
                .filter(b -> !b.isEmpty())
                .ifPresent(b -> {
                    throw new BusinessException("La póliza de Salud no permite el registro de vehículos.");
                });

        if (!Optional.ofNullable(policy.getBeneficiaries()).isEmpty()) {

            boolean haveParents = policy.getBeneficiaries().stream()
                    .anyMatch(b -> b.getParentesco() == RelationshipType.PADRE ||
                            b.getParentesco() == RelationshipType.MADRE);

            Boolean haveOwnFamily = policy.getBeneficiaries().stream()
                    .anyMatch(b -> b.getParentesco() == RelationshipType.HIJO ||
                            b.getParentesco() == RelationshipType.HIJA ||
                            b.getParentesco() == RelationshipType.ESPOSA ||
                            b.getParentesco() == RelationshipType.ESPOSO);

            if (haveParents && haveOwnFamily)
                throw new BusinessException(
                        "La poliza de salud solo permite registro del cliente, sus padres o su familia propia ");

        }

    }

    @Override
    public Policy getPolicyDetail(Long policyId) {
        Policy policy = policyRepositoryPort.findById(policyId).orElse(null);
        if (Optional.ofNullable(policy).isEmpty()) {
            throw new BusinessException("No se encontró la póliza con ID: " + policyId);
        }
        return policy;

    }

    @Override
    public List<Policy> findByPolicyListByClient(Long customerId) {
        return policyRepositoryPort.findByCustomerId(customerId);
    }

    @Override
    public List<Policy> findAllPolicies() {
        return policyRepositoryPort.findAll();
    }

    @Override
    public List<Beneficiary> findBeneficiaryByPolicyId(Long policyId) {
        return beneficiaryRepositoryPort.findBeneficiaryByPolicyId(policyId);
    }
}
