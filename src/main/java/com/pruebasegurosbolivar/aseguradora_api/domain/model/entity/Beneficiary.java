package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entidad para el manejo de beneficiarios (Póliza Vida) o familiares (Póliza
 * Salud).
 * Representa a las personas adicionales cubiertas o beneficiadas por una póliza.
 */
@Entity
@Table(name = "beneficiaries")
public class Beneficiary {

    /**
     * Identificador único del beneficiario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * La póliza a la que está asociado el beneficiario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonIgnoreProperties("beneficiaries") // Evita que el beneficiario serialice la póliza de nuevo
    private Policy policy;

    /**
     * Nombres del beneficiario.
     */
    private String nombres;
    /**
     * Apellidos del beneficiario.
     */
    private String apellidos;

    /**
     * Parentesco del beneficiario con el tomador de la póliza.
     */
    @Enumerated(EnumType.STRING)
    private RelationshipType parentesco;

    /**
     * Número de documento del beneficiario.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Beneficiary() {
    }

    /**
     * Constructor con todos los campos para inicializar un beneficiario.
     *
     * @param id Identificador único del beneficiario.
     * @param policy La póliza a la que está asociado.
     * @param nombres Nombres del beneficiario.
     * @param apellidos Apellidos del beneficiario.
     * @param parentesco Relación con el titular de la póliza.
     * @param numeroDocumento Número de documento de identidad.
     */
    public Beneficiary(Long id, Policy policy, String nombres, String apellidos, RelationshipType parentesco,
            String numeroDocumento) {
        this.id = id;
        this.policy = policy;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.parentesco = parentesco;
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Obtiene el identificador único del beneficiario.
     *
     * @return El ID del beneficiario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del beneficiario.
     *
     * @param id El nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la póliza asociada a este beneficiario.
     *
     * @return La entidad Policy.
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * Asocia el beneficiario a una póliza específica.
     *
     * @param policy La póliza a asociar.
     */
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    /**
     * Obtiene los nombres del beneficiario.
     *
     * @return Los nombres.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del beneficiario.
     *
     * @param nombres Los nombres a establecer.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del beneficiario.
     *
     * @return Los apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del beneficiario.
     *
     * @param apellidos Los apellidos a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el tipo de parentesco con el titular.
     *
     * @return El enum RelationshipType.
     */
    public RelationshipType getParentesco() {
        return parentesco;
    }

    /**
     * Establece el parentesco del beneficiario.
     *
     * @param parentesco El tipo de relación.
     */
    public void setParentesco(RelationshipType parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * Obtiene el número de documento del beneficiario.
     *
     * @return El número de documento como String.
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento del beneficiario.
     *
     * @param numeroDocumento El número de documento.
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
