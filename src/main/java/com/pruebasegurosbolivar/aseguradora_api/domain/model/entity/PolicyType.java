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

    /**
     * Constructor por defecto requerido por JPA.
     */
    public PolicyType() {
    }

    /**
     * Constructor con todos los campos para inicializar un tipo de póliza.
     *
     * @param id Identificador único del tipo de póliza.
     * @param nombre Nombre descriptivo (ej. Vida, Salud).
     * @param observaciones Notas adicionales sobre el tipo de póliza.
     */
    public PolicyType(Integer id, String nombre, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.observaciones = observaciones;
    }

    /**
     * Obtiene el identificador único del tipo de póliza.
     *
     * @return El ID del tipo de póliza.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único del tipo de póliza.
     *
     * @param id El nuevo ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del tipo de póliza.
     *
     * @return El nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del tipo de póliza.
     *
     * @param nombre El nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las observaciones o notas adicionales.
     *
     * @return Las observaciones.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones o notas adicionales.
     *
     * @param observaciones Las observaciones.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
