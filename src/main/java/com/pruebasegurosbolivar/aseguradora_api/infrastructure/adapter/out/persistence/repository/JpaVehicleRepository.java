package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;


/**
 * Repositorio JPA para la entidad Vehicle.
 */
@Repository
public interface JpaVehicleRepository extends JpaRepository<Vehicle, Long> {
    /**
     * Busca un vehículo por su placa.
     * @param placa La placa del vehículo.
     * @return Un Optional que contiene el vehículo si se encuentra.
     */
    Optional<Vehicle> findByPlaca(String placa);
}