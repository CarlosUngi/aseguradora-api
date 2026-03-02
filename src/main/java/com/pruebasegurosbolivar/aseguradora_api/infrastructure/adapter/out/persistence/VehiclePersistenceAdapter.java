package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.VehicleRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Adaptador de persistencia para la entidad Vehicle.
 * Implementa el puerto de salida para interactuar con la base de datos a través de JPA.
 */
@Component
@RequiredArgsConstructor
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {
    private final JpaVehicleRepository jpaVehicleRepository;

    /**
     * Busca un vehículo por su número de placa.
     *
     * @param placa La placa del vehículo a buscar.
     * @return Un Optional que contiene el vehículo si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Vehicle> findByPlaca(String placa) {
        return jpaVehicleRepository.findByPlaca(placa);
    }
}