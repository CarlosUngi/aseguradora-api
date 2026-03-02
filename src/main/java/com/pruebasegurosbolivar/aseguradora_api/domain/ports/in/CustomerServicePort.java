package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Puerto de entrada que define las operaciones de negocio para la gestión de clientes.
 * Define los contratos para la creación, consulta, actualización y eliminación de clientes.
 */
public interface CustomerServicePort {

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param customer Objeto con la información del cliente a crear.
     * @return El cliente persistido con su ID generado.
     */
    Customer create(Customer customer);

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id El ID del cliente.
     * @return Un Optional con el cliente si existe, o vacío si no.
     */
    Optional<Customer> findById(Long id);

    /**
     * Recupera una página de clientes.
     *
     * @param pageable Configuración de paginación (página, tamaño, orden).
     * @return Página de clientes encontrados.
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param id El ID del cliente a actualizar.
     * @param customer La entidad con la información actualizada.
     * @return El cliente actualizado.
     */
    Customer update(Long id, Customer customer);

    /**
     * Elimina un cliente del sistema por su ID.
     *
     * @param id El identificador del cliente a eliminar.
     */
    void delete(Long id);
}
