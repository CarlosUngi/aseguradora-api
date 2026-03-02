package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;
import java.util.Optional;

/**
 * Puerto de salida para la persistencia de vehículos.
 * Define las operaciones necesarias para interactuar con el repositorio de datos de vehículos.
 */
public interface VehicleRepositoryPort {

    /**
     * Busca un vehículo por su número de placa.
     *
     * @param placa La placa del vehículo a buscar.
     * @return Un Optional que contiene el vehículo si se encuentra, o vacío si no.
     */
    Optional<Vehicle> findByPlaca(String placa);
}