package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada para la gestión de clientes.
 * Define qué puede hacer el mundo exterior con los clientes.
 */
public interface CustomerServicePort {
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer update(Long id, Customer customer);
    void delete(Long id);
}
