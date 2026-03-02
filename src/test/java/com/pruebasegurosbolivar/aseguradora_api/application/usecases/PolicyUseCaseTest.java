package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.*;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.*;
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

/**
 * Pruebas unitarias para la clase PolicyUseCase.
 * Verifica las reglas de negocio específicas para la creación de pólizas de Vida, Vehículo y Salud.
 */
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

    /**
     * Configuración inicial para cada prueba.
     * Inicializa los objetos simulados y los datos de prueba comunes.
     */
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

    /**
     * Verifica que no se permita crear una segunda póliza de Vida para un cliente que ya tiene una vigente.
     */
    @Test
    @DisplayName("Vida: No debe permitir más de una póliza de vida por cliente")
    void createLifePolicyDuplicateError() {
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));
        when(policyRepositoryPort.findByCustomerId(1L)).thenReturn(Collections.singletonList(lifePolicy));

        assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(lifePolicy));
    }

    /**
     * Verifica que una póliza de Vida no exceda el límite máximo de beneficiarios permitidos (2).
     */
    @Test
    @DisplayName("Vida: No debe permitir más de 2 beneficiarios")
    void createLifePolicyMaxBeneficiariesError() {
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(customer));
        when(policyRepositoryPort.findByCustomerId(1L)).thenReturn(new ArrayList<>());
        
        lifePolicy.getBeneficiaries().add(new Beneficiary());
        lifePolicy.getBeneficiaries().add(new Beneficiary());
        lifePolicy.getBeneficiaries().add(new Beneficiary());

        BusinessException exception = assertThrows(BusinessException.class, () -> policyUseCase.createPolicy(lifePolicy));
        assertTrue(exception.getMessage().toLowerCase().contains("maximo 2 beneficiarios"));
    }

    /**
     * Verifica que una póliza de Vehículo rechace la creación si no se asocia al menos un vehículo.
     */
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

    /**
     * Verifica la regla de consanguinidad en pólizas de Salud.
     * No se permite mezclar beneficiarios de tipo padres con el núcleo familiar propio (hijos/cónyuge).
     */
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
