package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.BeneficiaryRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaBeneficiaryRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador de persistencia para la entidad Beneficiary.
 * Implementa el puerto de salida para interactuar con la base de datos a través de JPA.
 */
@Component
@RequiredArgsConstructor 
public class BeneficiaryPersistence implements BeneficiaryRepositoryPort {
    
    private final JpaBeneficiaryRepository jpaBeneficiaryRepository;
    
    /**
     * Busca los beneficiarios asociados a una póliza específica.
     *
     * @param policyId Identificador de la póliza.
     * @return Lista de beneficiarios encontrados.
     */
    @Override
    public List<Beneficiary> findBeneficiaryByPolicyId(Long policyId) {
        return jpaBeneficiaryRepository.findByPolicyId(policyId);
    }

    /**
     * Guarda o actualiza un beneficiario en la base de datos.
     *
     * @param beneficiary Entidad del beneficiario a persistir.
     * @return El beneficiario guardado.
     */
    @Override
    public Beneficiary save(Beneficiary beneficiary) {
        return jpaBeneficiaryRepository.save(beneficiary);
    }
    
}
