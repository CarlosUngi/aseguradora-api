package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidad núcleo del sistema que gestiona los contratos de seguros.
 * Centraliza la relación con clientes, tipos de póliza, beneficiarios y vehículos.
 */
@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_type_id", nullable = false)
    private PolicyType policyType;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "tarifa_total")
    private BigDecimal tarifaTotal;

    private String estado;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiaries;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "policy_vehicle",
        joinColumns = @JoinColumn(name = "policy_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles;

    public Policy() {
    }

    public Policy(Long id, Customer customer, PolicyType policyType, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal tarifaTotal, String estado, List<Beneficiary> beneficiaries, List<Vehicle> vehicles) {
        this.id = id;
        this.customer = customer;
        this.policyType = policyType;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tarifaTotal = tarifaTotal;
        this.estado = estado;
        this.beneficiaries = beneficiaries;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getTarifaTotal() {
        return tarifaTotal;
    }

    public void setTarifaTotal(BigDecimal tarifaTotal) {
        this.tarifaTotal = tarifaTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
