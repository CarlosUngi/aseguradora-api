package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import jakarta.persistence.*;

/**
 * Catálogo maestro para definir los tipos de póliza disponibles.
 * Ejemplo: 1 - Vida, 2 - Vehículo, 3 - Salud.
 */
@Entity
@Table(name = "policy_types")
public class PolicyType {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String observaciones;

    public PolicyType() {
    }

    public PolicyType(Integer id, String nombre, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
