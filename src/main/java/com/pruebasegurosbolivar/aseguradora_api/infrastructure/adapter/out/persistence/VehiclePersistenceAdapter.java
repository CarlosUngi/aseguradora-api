package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.VehicleRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {
    private final JpaVehicleRepository jpaVehicleRepository;

    @Override
    public Optional<Vehicle> findByPlaca(String placa) {
        return jpaVehicleRepository.findByPlaca(placa);
    }
}