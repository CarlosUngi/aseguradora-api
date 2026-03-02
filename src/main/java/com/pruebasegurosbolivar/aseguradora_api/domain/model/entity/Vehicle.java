package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un vehículo en el sistema.
 * Se relaciona con las pólizas mediante una relación muchos a muchos.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle {

    /**
     * Identificador único del vehículo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Placa única del vehículo.
     */
    @Column(unique = true, nullable = false)
    private String placa;

    /**
     * Marca del vehículo.
     */
    private String marca;
    /**
     * Modelo o línea del vehículo.
     */
    private String modelo;
    /**
     * Año del modelo del vehículo.
     */
    private String anio;

    /**
     * Lista de pólizas en las que está asegurado el vehículo.
     */
    @ManyToMany(mappedBy = "vehicles")
    @JsonIgnoreProperties("vehicles") // Evita el bucle en la relación muchos a muchos
    private List<Policy> policies;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Vehicle() {
    }

    /**
     * Constructor con todos los campos para inicializar un vehículo.
     *
     * @param id Identificador único del vehículo.
     * @param placa Placa única del vehículo.
     * @param marca Marca del vehículo.
     * @param modelo Modelo o línea del vehículo.
     * @param anio Año del modelo del vehículo.
     * @param policies Lista de pólizas asociadas.
     */
    public Vehicle(Long id, String placa, String marca, String modelo, String anio, List<Policy> policies) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.policies = policies;
    }

    /**
     * Obtiene el identificador único del vehículo.
     *
     * @return El ID del vehículo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del vehículo.
     *
     * @param id El nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la placa del vehículo.
     *
     * @return La placa.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa del vehículo.
     *
     * @param placa La placa.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return La marca.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param marca La marca.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo o línea del vehículo.
     *
     * @return El modelo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo o línea del vehículo.
     *
     * @param modelo El modelo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el año del modelo del vehículo.
     *
     * @return El año.
     */
    public String getAnio() {
        return anio;
    }

    /**
     * Establece el año del modelo del vehículo.
     *
     * @param anio El año.
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la lista de pólizas en las que está asegurado el vehículo.
     *
     * @return Lista de pólizas.
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Establece la lista de pólizas asociadas al vehículo.
     *
     * @param policies Lista de pólizas.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
