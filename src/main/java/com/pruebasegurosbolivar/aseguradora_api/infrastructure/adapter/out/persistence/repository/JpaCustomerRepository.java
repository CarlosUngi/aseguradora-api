package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Customer.
 */
@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * Busca un cliente por su número de documento.
     * @param numeroDocumento El número de documento del cliente.
     * @return Un Optional que contiene el cliente si se encuentra.
     */
    Optional<Customer> findByNumeroDocumento(String numeroDocumento);
}
