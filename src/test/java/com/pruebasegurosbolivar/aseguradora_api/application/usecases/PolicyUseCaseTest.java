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
