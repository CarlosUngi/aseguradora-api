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
