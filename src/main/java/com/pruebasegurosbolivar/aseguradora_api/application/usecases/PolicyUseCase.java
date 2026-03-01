package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
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
    
    /**
     * {@inheritDoc}
     * <p>Implementa la lógica de guardado directo mediante el adaptador de persistencia.</p>
     */
    @Override
    public Policy createPolicy(Policy policy) {
        // TODO: Implementar validación de póliza de vida aquí
        return policyRepositoryPort.save(policy);
    }

    @Override
    public Policy getPolicyDetail(Long policyId) {
        return policyRepositoryPort.findById(policyId).orElse(null);
    }

    @Override
    public List<Policy> findByPolicyListByClient(Long customerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPolicyListByClient'");
    }

    @Override
    public List<Beneficiary> findBeneficiaryByPolicyId(Long policyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBeneficiaryByPolicyId'");
    }
}
