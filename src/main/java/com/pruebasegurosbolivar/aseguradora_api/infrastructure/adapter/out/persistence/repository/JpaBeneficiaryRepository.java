package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface JpaBeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
    List<Beneficiary> findByPolicyId(Long policyId);
}
