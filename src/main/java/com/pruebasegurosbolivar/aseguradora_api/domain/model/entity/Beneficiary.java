package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entidad para el manejo de beneficiarios (Póliza Vida) o familiares (Póliza
 * Salud).
 */
@Entity
@Table(name = "beneficiaries")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonIgnoreProperties("beneficiaries") // Evita que el beneficiario serialice la póliza de nuevo
    private Policy policy;

    private String nombres;
    private String apellidos;

    @Enumerated(EnumType.STRING)
    private RelationshipType parentesco;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    public Beneficiary() {
    }

    public Beneficiary(Long id, Policy policy, String nombres, String apellidos, RelationshipType parentesco,
            String numeroDocumento) {
        this.id = id;
        this.policy = policy;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.parentesco = parentesco;
        this.numeroDocumento = numeroDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public RelationshipType getParentesco() {
        return parentesco;
    }

    public void setParentesco(RelationshipType parentesco) {
        this.parentesco = parentesco;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
