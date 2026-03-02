package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia para la entidad Policy.
 * Implementa el puerto de salida para interactuar con la base de datos a través de JPA.
 */
@Component
@RequiredArgsConstructor
public class PolicyPersistenceAdapter implements PolicyRepositoryPort {

    private final JpaPolicyRepository jpaPolicyRepository;

    /**
     * Guarda o actualiza una póliza en la base de datos.
     *
     * @param policy La entidad de la póliza a persistir.
     * @return La póliza guardada.
     */
    @Override
    public Policy save(Policy policy) {
        return jpaPolicyRepository.save(policy);
    }

    /**
     * Busca una póliza por su identificador único.
     *
     * @param id El ID de la póliza.
     * @return Un Optional con la póliza si existe.
     */
    @Override
    public Optional<Policy> findById(Long id) {
        return jpaPolicyRepository.findById(id);
    }

    /**
     * Recupera todas las pólizas registradas en el sistema.
     *
     * @return Lista de todas las pólizas.
     */
    @Override
    public List<Policy> findAll() {
        return jpaPolicyRepository.findAll();
    }

    /**
     * Elimina una póliza por su ID.
     *
     * @param id El identificador de la póliza.
     */
    @Override
    public void deleteById(Long id) {
        jpaPolicyRepository.deleteById(id);
    }

    /**
     * Busca todas las pólizas asociadas a un cliente específico.
     *
     * @param customerId El ID del cliente.
     * @return Lista de pólizas del cliente.
     */
    @Override
    public List<Policy> findByCustomerId(Long customerId) {
       return jpaPolicyRepository.findByCustomerId(customerId);
    
    }


}