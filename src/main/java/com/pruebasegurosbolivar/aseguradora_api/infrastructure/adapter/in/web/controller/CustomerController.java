package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Operation(summary = "Listar todos los clientes", description = "Retorna un listado de todos los clientes en la base de datos")
    @GetMapping
    public List<Customer> getAll() {
        return customerServicePort.findAll();
    }
/**
     * Crea un nuevo cliente en el sistema.
     * * @param customer Datos del cliente enviados en el cuerpo de la petición.
     * @return ResponseEntity con el cliente creado y estado 200 OK.
     */
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerServicePort.create(customer));
    }
}
