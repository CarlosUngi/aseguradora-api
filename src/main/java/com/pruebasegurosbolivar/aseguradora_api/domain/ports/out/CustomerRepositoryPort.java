package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Puerto de salida para persistencia de clientes.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 * Define las operaciones necesarias para interactuar con el repositorio de datos de clientes.
 */
public interface CustomerRepositoryPort {

    /**
     * Guarda o actualiza la información de un cliente.
     *
     * @param customer La entidad del cliente a persistir.
     * @return El cliente persistido.
     */
    Customer save(Customer customer);

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id El identificador del cliente.
     * @return Un Optional que contiene el cliente si se encuentra, o vacío si no.
     */
    Optional<Customer> findById(Long id);

    /**
     * Recupera una página de clientes.
     *
     * @param pageable La información de paginación.
     * @return Una página de clientes.
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id El identificador del cliente a eliminar.
     */
    void deleteById(Long id);

    /**
     * Busca un cliente por su número de documento.
     *
     * @param numero El número de documento del cliente.
     * @return Un Optional que contiene el cliente si se encuentra, o vacío si no.
     */
    Optional<Customer> findByNumeroDocumento(String numero);
}
