package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import java.util.List;

/**
 * Puerto de entrada para la gestión de pólizas y sus reglas de negocio.
 */
public interface PolicyServicePort {
    Policy createPolicy(Policy policy);
    List<Policy> findByPolicyListByClient(Long customerId);
    Policy getPolicyDetail(Long policyId);
    List<Beneficiary> findBeneficiaryByPolicyId(Long policyId);
}
