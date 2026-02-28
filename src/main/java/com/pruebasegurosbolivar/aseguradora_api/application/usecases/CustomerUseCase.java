package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Customer> findAll() {
        return customerRepositoryPort.findAll();
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
