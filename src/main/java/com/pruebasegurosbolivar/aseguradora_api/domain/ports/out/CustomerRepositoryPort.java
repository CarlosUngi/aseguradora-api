package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de clientes.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 */
public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    void deleteById(Long id);
}
