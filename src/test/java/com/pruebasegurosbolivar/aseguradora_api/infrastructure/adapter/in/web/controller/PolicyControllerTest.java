package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.application.usecases.PolicyUseCase;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.exception.BusinessException;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.PolicyServicePort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper.PolicyMapper;
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
    private PolicyMapper createLifePolicyMaxBeneficiariesError;

    @Test
    @DisplayName("GET /api/v1/policies/{id} debe retornar 400/404 si no existe")
    void getPolicyNotFound() throws Exception {
        // Cambiamos thenReturn(null) por thenThrow(...)
        when(policyServicePort.getPolicyDetail(999L))
                .thenThrow(new BusinessException("No encontrada"));

        mockMvc.perform(get("/api/v1/policies/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // El GlobalExceptionHandler devuelve 400 para BusinessException
                .andExpect(jsonPath("$.code").value("BUSINESS_ERROR"));
    }
}
