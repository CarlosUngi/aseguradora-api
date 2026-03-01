package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.BeneficiaryRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaBeneficiaryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class BeneficiaryPersistence implements BeneficiaryRepositoryPort {
    
    private final JpaBeneficiaryRepository jpaBeneficiaryRepository;
    
    @Override
    public List<Beneficiary> findBeneficiaryByPolicyId(Long policyId) {
        return jpaBeneficiaryRepository.findByPolicyId(policyId);
    }

    @Override
    public Beneficiary save(Beneficiary beneficiary) {
        return jpaBeneficiaryRepository.save(beneficiary);
    }
    
}
