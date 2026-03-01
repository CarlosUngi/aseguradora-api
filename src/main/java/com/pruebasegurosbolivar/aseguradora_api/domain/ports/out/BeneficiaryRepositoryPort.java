package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import java.util.List;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;

public interface  BeneficiaryRepositoryPort {
    List<Beneficiary> findBeneficiaryByPolicyId(Long policyId);
    Beneficiary save(Beneficiary beneficiary);  

    
}
