package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**

/**
 * Puerto de salida para persistencia de clientes.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 */
public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Page<Customer> findAll(Pageable pageable);
    void deleteById(Long id);
}
