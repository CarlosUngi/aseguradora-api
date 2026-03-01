package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los casos de uso para pólizas.
 * Esta clase actúa como el orquestador entre los puertos de entrada y salida.
 */
@Service
@RequiredArgsConstructor
public class PolicyUseCase implements PolicyServicePort {

    private final PolicyRepositoryPort policyRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;

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
    }

    private void validateSalud(Policy policy) {
        Optional.ofNullable(policy.getVehicles())
                .filter(b -> !b.isEmpty())
                .ifPresent(b -> {
                    throw new BusinessException("La póliza de Salud no permite el registro de vehículos.");
                });
        Optional.ofNullable(policy.getBeneficiaries())
                .filter(list -> !list.isEmpty())
                .orElseThrow(
                        () -> new BusinessException("Debe incluir al menos un vehículo para este tipo de póliza."));


    }

    @Override
    public Policy getPolicyDetail(Long policyId) {
        return policyRepositoryPort.findById(policyId).orElse(null);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBeneficiaryByPolicyId'");
    }
}
