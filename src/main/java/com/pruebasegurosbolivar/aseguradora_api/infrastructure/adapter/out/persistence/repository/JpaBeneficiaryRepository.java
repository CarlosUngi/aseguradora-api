package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repositorio JPA para la entidad Beneficiary.
 */
@Repository
public interface JpaBeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
    /**
     * Busca los beneficiarios asociados a una póliza específica.
     * @param policyId Identificador de la póliza.
     * @return Lista de beneficiarios encontrados.
     */
    List<Beneficiary> findByPolicyId(Long policyId);
}
