package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaCustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de persistencia para la entidad Customer.
 * Implementa el puerto de salida para interactuar con la base de datos a través de JPA.
 */
@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;

    /**
     * Guarda o actualiza un cliente en la base de datos.
     *
     * @param customer La entidad del cliente a persistir.
     * @return El cliente guardado.
     */
    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id El ID del cliente.
     * @return Un Optional con el cliente si existe.
     */
    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id);
    }

    /**
     * Recupera una página de clientes.
     *
     * @param pageable Configuración de paginación.
     * @return Página de clientes.
     */
    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return jpaCustomerRepository.findAll(pageable);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id El identificador del cliente.
     */
    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    /**
     * Busca un cliente por su número de documento.
     *
     * @param numero El número de documento.
     * @return Un Optional con el cliente si existe.
     */
    @Override
    public Optional<Customer> findByNumeroDocumento(String numero) {
        return jpaCustomerRepository.findByNumeroDocumento(numero);
    }
}
