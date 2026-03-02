package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidad que representa a un cliente en el sistema de seguros.
 * Contiene la información personal básica y su relación con las pólizas contratadas.
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_documento", unique = true, nullable = false)
    private String numeroDocumento;

    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private Boolean activo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Policy> policies;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Customer() {
    }

    /**
     * Constructor con todos los campos para inicializar un cliente.
     *
     * @param id Identificador único del cliente.
     * @param tipoDocumento Tipo de documento de identidad.
     * @param numeroDocumento Número del documento de identidad.
     * @param nombres Nombres del cliente.
     * @param apellidos Apellidos del cliente.
     * @param email Correo electrónico de contacto.
     * @param telefono Teléfono de contacto.
     * @param fechaNacimiento Fecha de nacimiento.
     * @param policies Lista de pólizas asociadas.
     */
    public Customer(Long id, String tipoDocumento, String numeroDocumento, String nombres, String apellidos, String email, String telefono, LocalDate fechaNacimiento, List<Policy> policies) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.policies = policies;
        this.activo=true;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return El ID del cliente.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param id El nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de documento de identidad.
     *
     * @return El tipo de documento.
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Establece el tipo de documento de identidad.
     *
     * @param tipoDocumento El tipo de documento.
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Obtiene el número de documento de identidad.
     *
     * @return El número de documento.
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento de identidad.
     *
     * @param numeroDocumento El número de documento.
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Obtiene los nombres del cliente.
     *
     * @return Los nombres.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del cliente.
     *
     * @param nombres Los nombres.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del cliente.
     *
     * @return Los apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del cliente.
     *
     * @param apellidos Los apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return El email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param email El email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return El teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono El teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de nacimiento del cliente.
     *
     * @return La fecha de nacimiento.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del cliente.
     *
     * @param fechaNacimiento La fecha de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la lista de pólizas asociadas al cliente.
     *
     * @return Lista de pólizas.
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Establece la lista de pólizas asociadas al cliente.
     *
     * @param policies Lista de pólizas.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    /**
     * Indica si el cliente está activo en el sistema.
     *
     * @return true si está activo, false en caso contrario.
     */
    public Boolean getActive() {
        return activo;
    }

    /**
     * Establece el estado activo del cliente.
     *
     * @param activo true para activar, false para desactivar.
     */
    public void setActive(Boolean activo) {
        this.activo = activo;
    }
}
