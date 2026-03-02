package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Implementación de los casos de uso relacionados con la gestión de clientes.
 * Esta clase contiene la lógica de negocio para crear, consultar, actualizar y eliminar clientes.
 */
@Service
@RequiredArgsConstructor
public class CustomerUseCase implements CustomerServicePort {

    /**
     * Puerto del repositorio de clientes para interactuar con la capa de persistencia.
     */
    private final CustomerRepositoryPort customerRepositoryPort;

    /**
     * Crea un nuevo cliente en el sistema.
     * Valida que no exista otro cliente con el mismo número de documento.
     *
     * @param customer La entidad del cliente a crear.
     * @return El cliente creado.
     * @throws BusinessException si ya existe un cliente con el mismo número de documento.
     */
    @Override
    public Customer create(Customer customer) {
        customerRepositoryPort.findByNumeroDocumento(customer.getNumeroDocumento())
                .ifPresent(c -> {
                    throw new BusinessException("Ya existe un cliente registrado con el número de documento: "
                            + customer.getNumeroDocumento());
                });
        return customerRepositoryPort.save(customer);
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id El identificador único del cliente.
     * @return Un Optional que contiene el cliente si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepositoryPort.findById(id);
    }

    /**
     * Obtiene una lista paginada de todos los clientes.
     *
     * @param pageable La información de paginación.
     * @return Una página de clientes.
     */
    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepositoryPort.findAll(pageable);
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param id El ID del cliente a actualizar.
     * @param customer La entidad con la información actualizada.
     * @return El cliente actualizado.
     */
    @Override
    public Customer update(Long id, Customer customer) {
        return customerRepositoryPort.save(customer);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id El identificador del cliente a eliminar.
     */
    @Override
    public void delete(Long id) {
        customerRepositoryPort.deleteById(id);
    }
}
