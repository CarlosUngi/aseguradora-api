package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Policy.
 */
@Repository
public interface JpaPolicyRepository extends JpaRepository<Policy, Long> {
    /**
     * Busca las pólizas asociadas a un cliente específico.
     * @param customerId El ID del cliente.
     * @return Una lista de pólizas encontradas.
     */
    List<Policy> findByCustomerId(Long customerId);
}