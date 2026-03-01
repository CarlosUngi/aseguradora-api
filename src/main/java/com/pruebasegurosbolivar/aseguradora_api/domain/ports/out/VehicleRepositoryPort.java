package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import java.util.Optional;

public interface VehicleRepositoryPort {
    Optional<Vehicle> findByPlaca(String placa);
}