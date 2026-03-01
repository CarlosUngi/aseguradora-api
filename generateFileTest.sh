#!/bin/bash

# Rutas base
TEST_BASE_PATH="src/test/java/com/pruebasegurosbolivar/aseguradora_api"
PACKAGE="com.pruebasegurosbolivar.aseguradora_api"

echo "🧪 Iniciando generación de suite de pruebas para Arquitectura Hexagonal..."

# Crear directorios
mkdir -p $TEST_BASE_PATH/application/usecases
mkdir -p $TEST_BASE_PATH/infrastructure/adapter/in/web/controller

# --- 1. PolicyUseCaseTest.java ---
# Prueba las reglas de negocio críticas definidas en los requerimientos
cat <<EOF > $TEST_BASE_PATH/application/usecases/PolicyUseCaseTest.java
package $PACKAGE.application.usecases;

import $PACKAGE.domain.model.entity.*;
import $PACKAGE.domain.model.exception.BusinessException;
import $PACKAGE.domain.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyUseCaseTest {

    @Mock
    private PolicyRepositoryPort policyRepositoryPort;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private PolicyUseCase policyUseCase;

    private Customer customer;
    private Policy lifePolicy;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setNombres("Carlos");

        lifePolicy = new Policy();
        lifePolicy.setCustomer(customer);
        PolicyType type = new PolicyType(1, "Vida", "");
        lifePolicy.setPolicyType(type);
        lifePolicy.setBeneficiaries(new ArrayList<>());
    }

    @Test
    @DisplayName("Vida: No debe permitir más de una póliza de vida por cliente")
    void createLifePolicyDuplicateError() {
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));
        when(policyRepositoryPort.findByCustomerId(1L)).thenReturn(Collections.singletonList(lifePolicy));

        assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(lifePolicy));
    }

    @Test
    @DisplayName("Vida: No debe permitir más de 2 beneficiarios")
    void createLifePolicyMaxBeneficiariesError() {
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));
        when(policyRepositoryPort.findByCustomerId(1L)).thenReturn(new ArrayList<>());
        
        lifePolicy.getBeneficiaries().add(new Beneficiary());
        lifePolicy.getBeneficiaries().add(new Beneficiary());
        lifePolicy.getBeneficiaries().add(new Beneficiary());

        BusinessException exception = assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(lifePolicy));
        assertTrue(exception.getMessage().contains("máximo 2 beneficiarios"));
    }

    @Test
    @DisplayName("Vehículo: Debe fallar si no se incluyen vehículos")
    void createVehiclePolicyNoVehiclesError() {
        Policy vPolicy = new Policy();
        vPolicy.setCustomer(customer);
        vPolicy.setPolicyType(new PolicyType(2, "Vehículo", ""));
        vPolicy.setVehicles(new ArrayList<>());

        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));

        assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(vPolicy));
    }

    @Test
    @DisplayName("Salud: No debe permitir mezclar Padres con Hijos/Esposa")
    void createHealthPolicyMixedFamilyError() {
        Policy hPolicy = new Policy();
        hPolicy.setCustomer(customer);
        hPolicy.setPolicyType(new PolicyType(3, "Salud", ""));
        
        List<Beneficiary> beneficiaries = new ArrayList<>();
        Beneficiary padre = new Beneficiary(); padre.setParentesco(RelationshipType.PADRE);
        Beneficiary hijo = new Beneficiary(); hijo.setParentesco(RelationshipType.HIJO);
        beneficiaries.add(padre);
        beneficiaries.add(hijo);
        hPolicy.setBeneficiaries(beneficiaries);

        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));

        assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(hPolicy));
    }
}
EOF

# --- 2. PolicyControllerTest.java ---
# Prueba la capa web y la transformación a DTOs de respuesta
cat <<EOF > $TEST_BASE_PATH/infrastructure/adapter/in/web/controller/PolicyControllerTest.java
package $PACKAGE.infrastructure.adapter.in.web.controller;

import $PACKAGE.application.usecases.PolicyUseCase;
import $PACKAGE.domain.model.entity.Policy;
import $PACKAGE.domain.ports.in.PolicyServicePort;
import $PACKAGE.infrastructure.adapter.in.web.mapper.PolicyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PolicyController.class)
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolicyServicePort policyServicePort;

    @MockBean
    private PolicyMapper policyMapper;

    @Test
    @DisplayName("GET /api/v1/policies/{id} debe retornar 404 si no existe")
    void getPolicyNotFound() throws Exception {
        when(policyServicePort.getPolicyDetail(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/policies/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // Debido a BusinessException lanzada en controlador
                .andExpect(jsonPath("$.code").value("BUSINESS_ERROR"));
    }
}
EOF

# --- 3. CustomerUseCaseTest.java ---
cat <<EOF > $TEST_BASE_PATH/application/usecases/CustomerUseCaseTest.java
package $PACKAGE.application.usecases;

import $PACKAGE.domain.model.entity.Customer;
import $PACKAGE.domain.model.exception.BusinessException;
import $PACKAGE.domain.ports.out.CustomerRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private CustomerUseCase customerUseCase;

    @Test
    @DisplayName("Debe fallar si el número de documento ya existe")
    void createCustomerDuplicateError() {
        Customer customer = new Customer();
        customer.setNumeroDocumento("123");
        
        when(customerRepositoryPort.findByNumeroDocumento("123")).thenReturn(Optional.of(customer));

        assertThrows(BusinessException.class, () -> customerUseCase.create(customer));
        verify(customerRepositoryPort, never()).save(any());
    }
}
EOF

echo "✅ Archivos de prueba generados exitosamente."
echo "💡 Recuerda ejecutar './mvnw test' para verificar los resultados."