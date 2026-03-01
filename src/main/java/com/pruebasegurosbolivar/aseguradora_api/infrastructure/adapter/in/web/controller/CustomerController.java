package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
/**
 * Adaptador de entrada REST para la gestión de clientes.
 * Expone los endpoints necesarios para el CRUD de la entidad Customer.
 */
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Clientes", description = "Operaciones permitidas sobre la entidad Cliente")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServicePort customerServicePort;

    /**
     * {@inheritDoc}
     * Obtiene la lista de clientes con soporte para paginación.
     * 
     * @param pageable Configuración de página (ej: ?page=0&size=10).
     * @return Página de clientes.
     */
    @Operation(summary = "Listar clientes paginados", description = "Retorna una página de clientes. Se pueden usar parámetros 'page' y 'size'.")
    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerServicePort.findAll(pageable));
    }

    /**
     * Consulta un cliente específico por su identificador único.
     * * @param id Identificador único del cliente.
     * 
     * @return {@link ResponseEntity} con el cliente si existe, o 404 si no es
     *         encontrado.
     */
    @Operation(summary = "Consultar cliente por ID", description = "Busca un cliente específico utilizando su ID primario.")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return customerServicePort.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo cliente en el sistema.
     * * @param customer Datos del cliente enviados en el cuerpo de la petición.
     * 
     * @return ResponseEntity con el cliente creado y estado 200 OK.
     */
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en la base de datos")
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerCreateRequest request) {
        Customer customer = new Customer();
        customer.setTipoDocumento(request.getTipoDocumento());
        customer.setNumeroDocumento(request.getNumeroDocumento());
        customer.setNombres(request.getNombres());
        customer.setApellidos(request.getApellidos());
        customer.setEmail(request.getEmail());
        customer.setTelefono(request.getTelefono());
        customer.setFechaNacimiento(request.getFechaNacimiento());
        return ResponseEntity.ok(customerServicePort.create(customer));
    }

    /**
     * Obtiene los detalles de un cliente por su ID.
     * 
     * @param Customer
     * @return ResponseEntity con la objeto editado del cliente y estado 200 OK.
     */
    @Operation(summary = "Actualizar un cliente", description = "Actualiza un cliente en la base de datos")
    @PutMapping
    public ResponseEntity<Customer> update(@Valid @RequestBody CustomerUpdateRequest request) {
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setTipoDocumento(request.getTipoDocumento());
        customer.setNumeroDocumento(request.getNumeroDocumento());
        customer.setNombres(request.getNombres());
        customer.setApellidos(request.getApellidos());
        customer.setEmail(request.getEmail());
        customer.setTelefono(request.getTelefono());
        customer.setFechaNacimiento(request.getFechaNacimiento());
        return ResponseEntity.ok(customerServicePort.update(request.getId(), customer));
    }

    /**
     * Hace borrado logico de un cliente por su ID.
     * 
     * @param idClient
     * @return void y estado 200 OK.
     */
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente en la base de datos")
    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> delete(@PathVariable Long idClient) {
        customerServicePort.delete(idClient);
        return ResponseEntity.ok().build();
    }

}
