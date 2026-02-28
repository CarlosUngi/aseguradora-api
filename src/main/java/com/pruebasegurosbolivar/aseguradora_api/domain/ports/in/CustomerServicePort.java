package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Puerto de entrada que define las operaciones de negocio para la gestión de pólizas.
 * Coordina la creación y consulta de seguros de Vida, Vehículo y Salud.
 */
public interface CustomerServicePort {
    /**
     * Registra una nueva póliza en eimport java.util.List;
l sistema validando las reglas de negocio específicas.
     * * @param policy Objeto con la información de la póliza a crear.
     * @return La póliza persistida con su ID generado.
     * @throws IllegalArgumentException si la póliza de vida ya existe para el cliente.
     */
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
    /**
     * Recupera una página de clientes.
     * @param pageable Configuración de paginación (página, tamaño, orden).
     * @return Página de clientes encontrados.
     */
    Page<Customer> findAll(Pageable pageable);
    Customer update(Long id, Customer customer);
    void delete(Long id);
}
