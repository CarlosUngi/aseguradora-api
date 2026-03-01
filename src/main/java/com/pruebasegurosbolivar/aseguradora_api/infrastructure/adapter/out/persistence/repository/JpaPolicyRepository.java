package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByCustomerId(Long customerId);
}