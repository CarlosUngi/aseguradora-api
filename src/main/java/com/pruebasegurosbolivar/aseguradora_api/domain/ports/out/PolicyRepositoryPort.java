package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de polizas.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 */
public interface PolicyRepositoryPort {
    Policy save(Policy customer);
    Optional<Policy> findById(Long id);
    List<Policy> findAll();
    void deleteById(Long id);
}
