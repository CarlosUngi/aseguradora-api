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
                validateVida(policy);
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

    private void validateVida(Policy policy) {
        if ((policy.getBeneficiaries() != null && !policy.getBeneficiaries().isEmpty()) ||
                (policy.getVehicles() != null && !policy.getVehicles().isEmpty())) {
            throw new BusinessException(
                    "La póliza de Vida no puede contener beneficiarios ni vehículos en la creación.");
        }
    }

    private void validateVehiculo(Policy policy) {
        if (policy.getBeneficiaries() != null && !policy.getBeneficiaries().isEmpty()) {
            throw new BusinessException("La póliza de Vehículo no permite el registro de beneficiarios.");
        }
        if (policy.getVehicles() == null || policy.getVehicles().isEmpty()) {
            throw new BusinessException("Debe incluir al menos un vehículo para este tipo de póliza.");
        }
    }

    private void validateSalud(Policy policy) {
        if (policy.getVehicles() != null && !policy.getVehicles().isEmpty()) {
            throw new BusinessException("La póliza de Salud no permite el registro de vehículos.");
        }

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
