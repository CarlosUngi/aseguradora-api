package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad núcleo del sistema que gestiona los contratos de seguros.
 * Centraliza la relación con clientes, tipos de póliza, beneficiarios y
 * vehículos.
 */
@Entity
@Table(name = "policies")
public class Policy {

    /**
     * Identificador único de la póliza.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cliente titular de la póliza.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * Tipo de póliza (Vida, Vehículo, Salud).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_type_id", nullable = false)
    private PolicyType policyType;

    /**
     * Fecha de inicio de vigencia de la póliza.
     */
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    /**
     * Fecha de fin de vigencia de la póliza.
     */
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    /**
     * Costo total de la póliza.
     */
    @Column(name = "tarifa_total")
    private BigDecimal tarifaTotal;

    /**
     * Estado actual de la póliza (ej. Activa, Inactiva).
     */
    private String estado;

    /**
     * Lista de beneficiarios asociados a la póliza.
     */
    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiaries;

    /**
     * Lista de vehículos asegurados en la póliza.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "policy_vehicle", joinColumns = @JoinColumn(name = "policy_id"), inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
    private List<Vehicle> vehicles;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Policy() {
    }

    /**
     * Constructor con todos los campos para inicializar una póliza.
     *
     * @param id Identificador único de la póliza.
     * @param customer Cliente titular de la póliza.
     * @param policyType Tipo de póliza (Vida, Vehículo, Salud).
     * @param fechaInicio Fecha de inicio de vigencia.
     * @param fechaFin Fecha de fin de vigencia.
     * @param tarifaTotal Costo total de la póliza.
     * @param estado Estado actual de la póliza (ej. Activa, Inactiva).
     * @param beneficiaries Lista de beneficiarios asociados.
     * @param vehicles Lista de vehículos asegurados.
     */
    public Policy(Long id, Customer customer, PolicyType policyType, LocalDate fechaInicio, LocalDate fechaFin,
            BigDecimal tarifaTotal, String estado, List<Beneficiary> beneficiaries, List<Vehicle> vehicles) {
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

    /**
     * Agrega un beneficiario a la póliza y establece la relación bidireccional.
     *
     * @param beneficiary El beneficiario a agregar.
     */
    public void addBeneficiary(Beneficiary beneficiary) {
        if (this.beneficiaries == null)
            this.beneficiaries = new ArrayList<>();
        this.beneficiaries.add(beneficiary);
        beneficiary.setPolicy(this); // Sincronización bidireccional
    }

    /**
     * Agrega un vehículo a la póliza y gestiona la relación muchos a muchos.
     *
     * @param vehicle El vehículo a agregar.
     */
    public void addVehicle(Vehicle vehicle) {
        if (this.vehicles == null)
            this.vehicles = new ArrayList<>();
        this.vehicles.add(vehicle);

        if (vehicle.getPolicies() == null)
            vehicle.setPolicies(new ArrayList<>());
        if (!vehicle.getPolicies().contains(this)) {
            vehicle.getPolicies().add(this);
        }
    }

    /**
     * Obtiene el identificador único de la póliza.
     *
     * @return El ID de la póliza.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la póliza.
     *
     * @param id El nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el cliente titular de la póliza.
     *
     * @return La entidad Customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Establece el cliente titular de la póliza.
     *
     * @param customer El cliente.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Obtiene el tipo de póliza.
     *
     * @return La entidad PolicyType.
     */
    public PolicyType getPolicyType() {
        return policyType;
    }

    /**
     * Establece el tipo de póliza.
     *
     * @param policyType El tipo de póliza.
     */
    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    /**
     * Obtiene la fecha de inicio de vigencia.
     *
     * @return La fecha de inicio.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio de vigencia.
     *
     * @param fechaInicio La fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin de vigencia.
     *
     * @return La fecha de fin.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin de vigencia.
     *
     * @param fechaFin La fecha de fin.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene la tarifa total de la póliza.
     *
     * @return El valor de la tarifa.
     */
    public BigDecimal getTarifaTotal() {
        return tarifaTotal;
    }

    /**
     * Establece la tarifa total de la póliza.
     *
     * @param tarifaTotal El valor de la tarifa.
     */
    public void setTarifaTotal(BigDecimal tarifaTotal) {
        this.tarifaTotal = tarifaTotal;
    }

    /**
     * Obtiene el estado actual de la póliza.
     *
     * @return El estado como String.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la póliza.
     *
     * @param estado El estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la lista de beneficiarios asociados.
     *
     * @return Lista de beneficiarios.
     */
    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     * Establece la lista de beneficiarios.
     *
     * @param beneficiaries Lista de beneficiarios.
     */
    public void setBeneficiaries(List<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    /**
     * Obtiene la lista de vehículos asegurados.
     *
     * @return Lista de vehículos.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Establece la lista de vehículos asegurados.
     *
     * @param vehicles Lista de vehículos.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
