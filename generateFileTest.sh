#!/bin/bash

# Definir las rutas base
MAIN_BASE_PATH="src/main/java/com/pruebasegurosbolivar/aseguradora_api"
TEST_BASE_PATH="src/test/java/com/pruebasegurosbolivar/aseguradora_api"

echo "🚀 Configurando componentes de negocio y entorno de pruebas..."

# 1. Asegurar que existan las carpetas de aplicación e infraestructura
mkdir -p $MAIN_BASE_PATH/application/usecases
mkdir -p $TEST_BASE_PATH/application/usecases
mkdir -p $TEST_BASE_PATH/infrastructure/adapter/in/web/controller

echo "📁 Estructura de carpetas verificada."

# 2. CREAR ESQUELETO DE PolicyUseCase (Para que los tests compilen)
# Este archivo es el que faltaba y causaba los errores en el IDE
cat <<EOF > $MAIN_BASE_PATH/application/usecases/PolicyUseCase.java
package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyUseCase implements PolicyServicePort {

    private final PolicyRepositoryPort policyRepositoryPort;

    @Override
    public Policy createPolicy(Policy policy) {
        // TODO: Implementar validación de póliza de vida aquí
        return policyRepositoryPort.save(policy);
    }

    @Override
    public List<Policy> findByCustomerId(Long customerId) {
        return policyRepositoryPort.findByCustomerId(customerId);
    }

    @Override
    public Policy getPolicyDetail(Long policyId) {
        return policyRepositoryPort.findById(policyId).orElse(null);
    }
}
EOF

# 3. Crear Test para CustomerUseCase
cat <<EOF > $TEST_BASE_PATH/application/usecases/CustomerUseCaseTest.java
package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @InjectMocks
    private CustomerUseCase customerUseCase;

    @Test
    @DisplayName("Debe crear un cliente correctamente")
    void createCustomerSuccess() {
        Customer customer = new Customer();
        customer.setNombres("Carlos");
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerUseCase.create(customer);

        assertNotNull(result);
        assertEquals("Carlos", result.getNombres());
        verify(customerRepositoryPort, times(1)).save(any(Customer.class));
    }
}
EOF

# 4. Crear Test para PolicyUseCase (Ahora ya encontrará la clase)
cat <<EOF > $TEST_BASE_PATH/application/usecases/PolicyUseCaseTest.java
package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PolicyUseCaseTest {

    @Mock
    private PolicyRepositoryPort policyRepositoryPort;

    @InjectMocks
    private PolicyUseCase policyUseCase;

    @Test
    @DisplayName("Debe guardar una póliza cuando no hay restricciones")
    void createPolicySuccess() {
        Policy policy = new Policy();
        when(policyRepositoryPort.save(any(Policy.class))).thenReturn(policy);

        Policy result = policyUseCase.createPolicy(policy);

        assertNotNull(result);
        verify(policyRepositoryPort, times(1)).save(policy);
    }
}
EOF

# 5. Crear Test para CustomerController
cat <<EOF > $TEST_BASE_PATH/infrastructure/adapter/in/web/controller/CustomerControllerTest.java
package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServicePort customerServicePort;

    @Test
    @DisplayName("GET /api/v1/customers debe retornar 200 OK")
    void getAllCustomersShouldReturnOk() throws Exception {
        when(customerServicePort.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
EOF

echo "✅ Clases de negocio y archivos de prueba generados."
echo "💡 Ejecuta 'mvn test' para verificar la cobertura."