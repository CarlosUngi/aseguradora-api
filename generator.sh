#!/bin/bash

# Definir la ruta base del paquete
BASE_PATH="src/main/java/com/pruebasegurosbolivar/aseguradora_api"

echo "🚀 Iniciando reestructuración a Arquitectura Hexagonal..."

# 1. Crear estructura de directorios
mkdir -p $BASE_PATH/domain/ports/in
mkdir -p $BASE_PATH/domain/ports/out
mkdir -p $BASE_PATH/domain/model/exception
mkdir -p $BASE_PATH/application/usecases
mkdir -p $BASE_PATH/infrastructure/adapter/in/web/controller
mkdir -p $BASE_PATH/infrastructure/adapter/in/web/dto
mkdir -p $BASE_PATH/infrastructure/adapter/out/persistence/repository
mkdir -p $BASE_PATH/infrastructure/config

echo "📁 Carpetas creadas exitosamente."

# 2. Definir archivos importantes (Puertos e Interfaces)

# --- DOMAIN PORTS (IN) ---
cat <<EOF > $BASE_PATH/domain/ports/in/CustomerServicePort.java
package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada para la gestión de clientes.
 * Define qué puede hacer el mundo exterior con los clientes.
 */
public interface CustomerServicePort {
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer update(Long id, Customer customer);
    void delete(Long id);
}
EOF

cat <<EOF > $BASE_PATH/domain/ports/in/PolicyServicePort.java
package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import java.util.List;

/**
 * Puerto de entrada para la gestión de pólizas y sus reglas de negocio.
 */
public interface PolicyServicePort {
    Policy createPolicy(Policy policy);
    List<Policy> findByCustomerId(Long customerId);
    Policy getPolicyDetail(Long policyId);
}
EOF

# --- DOMAIN PORTS (OUT) ---
cat <<EOF > $BASE_PATH/domain/ports/out/CustomerRepositoryPort.java
package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de clientes.
 * Desacopla el negocio de la tecnología de BD (H2, Postgres, etc).
 */
public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    void deleteById(Long id);
}
EOF

# --- APPLICATION USE CASES ---
cat <<EOF > $BASE_PATH/application/usecases/CustomerUseCase.java
package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUseCase implements CustomerServicePort {

    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public Customer create(Customer customer) {
        // TODO: Implementar lógica de creación
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepositoryPort.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepositoryPort.findAll();
    }

    @Override
    public Customer update(Long id, Customer customer) {
        // TODO: Implementar lógica de actualización
        return null;
    }

    @Override
    public void delete(Long id) {
        customerRepositoryPort.deleteById(id);
    }
}
EOF

# --- INFRASTRUCTURE ADAPTERS (OUT - PERSISTENCE) ---
cat <<EOF > $BASE_PATH/infrastructure/adapter/out/persistence/repository/JpaCustomerRepository.java
package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {
}
EOF

cat <<EOF > $BASE_PATH/infrastructure/adapter/out/persistence/CustomerPersistenceAdapter.java
package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}
EOF

# --- INFRASTRUCTURE ADAPTERS (IN - WEB) ---
cat <<EOF > $BASE_PATH/infrastructure/adapter/in/web/controller/CustomerController.java
package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServicePort customerServicePort;

    @GetMapping
    public List<Customer> getAll() {
        return customerServicePort.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerServicePort.create(customer));
    }
}
EOF

echo "✅ Estructura completada. Los archivos clave han sido generados sin lógica."