package com.pruebasegurosbolivar.aseguradora_api.application.usecases;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.CustomerRepositoryPort;
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
