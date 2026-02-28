package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import java.util.List;

/**
 * Puerto de entrada para la gestión de pólizas y sus reglas de negocio.
 */
public interface PolicyServicePort {
    Policy createPolicy(Policy policy);
    List<Policy> findByPolicyList();
    Policy getPolicyDetail(Long policyId);
}
