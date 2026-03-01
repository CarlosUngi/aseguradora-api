package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Vehicle;


@Repository
public interface JpaVehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlaca(String placa);
}