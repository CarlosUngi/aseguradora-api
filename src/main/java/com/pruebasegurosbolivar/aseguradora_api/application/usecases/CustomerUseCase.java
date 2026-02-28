package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class CustomerUseCase implements CustomerServicePort {

    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public Customer create(Customer customer) {
        // TODO: Implementar lógica de creación
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepositoryPort.findById(id);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable){
       /**
     * Delega la búsqueda paginada al puerto de salida (repositorio).
     */
       return customerRepositoryPort.findAll(pageable);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        // TODO: Implementar lógica de actualización
        return null;
    }

    @Override
    public void delete(Long id) {
        customerRepositoryPort.deleteById(id);
    }
}
