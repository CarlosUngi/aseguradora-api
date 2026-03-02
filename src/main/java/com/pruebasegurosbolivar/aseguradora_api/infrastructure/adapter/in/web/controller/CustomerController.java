package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerUpdateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper.CustomerMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador de entrada REST para la gestión de clientes.
 * Expone los endpoints necesarios para el CRUD de la entidad Customer.
 */
@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Clientes", description = "Operaciones permitidas sobre la entidad Cliente")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServicePort customerServicePort;
    private final CustomerMapper customerMapper;

    /**
     * Obtiene la lista de clientes con soporte para paginación.
     * 
     * @param pageable Configuración de página (ej: ?page=0&size=10).
     * @return Página de clientes.
     */
    @Operation(summary = "Listar clientes paginados", description = "Retorna una página de clientes. Se pueden usar parámetros 'page' y 'size'.")
    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerServicePort.findAll(pageable));
    }

    /**
     * Consulta un cliente específico por su identificador único.
     * 
     * @param id Identificador único del cliente.
     * @return {@link ResponseEntity} con el cliente si existe, o 404 si no es encontrado.
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
     * 
     * @param request Datos del cliente enviados en el cuerpo de la petición.
     * @return ResponseEntity con el cliente creado y estado 200 OK.
     */
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en la base de datos")
    @PostMapping
    public ResponseEntity<Customer> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(name = "Crear Cliente", summary = "Ejemplo para crear un nuevo cliente", value = "{\"tipoDocumento\": \"CC\", \"numeroDocumento\": \"123456789\", \"nombres\": \"Juan\", \"apellidos\": \"Perez\", \"email\": \"juan.perez@example.com\", \"telefono\": \"3001234567\", \"fechaNacimiento\": \"1990-01-15\"}")
            })) @Valid @RequestBody CustomerCreateRequest request) {
        Customer customer = customerMapper.toDomain(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerServicePort.create(customer));
    }

    /**
     * Obtiene los detalles de un cliente por su ID.
     * 
     * @param id Identificador del cliente a actualizar.
     * @param request Objeto con la información a actualizar.
     * @return ResponseEntity con el objeto editado del cliente y estado 200 OK.
     */
    @Operation(summary = "Actualizar un cliente", description = "Actualiza un cliente en la base de datos")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id,
            @Valid @RequestBody CustomerUpdateRequest request) {
        return customerServicePort.findById(id)
                .map(existingCustomer -> {
                    customerMapper.updateCustomerFromDto(request, existingCustomer);
                    return ResponseEntity.ok(customerServicePort.update(id, existingCustomer));
                })
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con id: " + id));
    }

    /**
     * Hace borrado logico de un cliente por su ID.
     * 
     * @param customerId Identificador del cliente a eliminar.
     * @return ResponseEntity vacío con estado 200 OK.
     */
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente en la base de datos")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerServicePort.delete(customerId);
        return ResponseEntity.ok().build();
    }

}
