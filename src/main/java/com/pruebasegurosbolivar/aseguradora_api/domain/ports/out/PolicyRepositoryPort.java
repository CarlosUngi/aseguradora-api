package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de polizas.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 * Define las operaciones necesarias para interactuar con el repositorio de datos de pólizas.
 */
public interface PolicyRepositoryPort {

    /**
     * Guarda o actualiza una póliza en la base de datos.
     *
     * @param policy La entidad de la póliza a persistir.
     * @return La póliza guardada.
     */
    Policy save(Policy policy);

    /**
     * Busca una póliza por su identificador único.
     *
     * @param id El identificador de la póliza.
     * @return Un Optional que contiene la póliza si se encuentra, o vacío si no.
     */
    Optional<Policy> findById(Long id);

    /**
     * Recupera todas las pólizas registradas en el sistema.
     *
     * @return Una lista con todas las pólizas.
     */
    List<Policy> findAll();

    /**
     * Elimina una póliza por su identificador.
     *
     * @param id El identificador de la póliza a eliminar.
     */
    void deleteById(Long id);

    /**
     * Busca todas las pólizas asociadas a un cliente específico.
     *
     * @param customerId El identificador del cliente.
     * @return Una lista de pólizas asociadas al cliente.
     */
    List<Policy> findByCustomerId(Long customerId);
}
