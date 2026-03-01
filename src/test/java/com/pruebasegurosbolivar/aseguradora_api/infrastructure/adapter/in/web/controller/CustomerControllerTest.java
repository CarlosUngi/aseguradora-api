package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.controller;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.in.CustomerServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    @DisplayName("Debe listar clientes de forma paginada")
    void findAllCustomersPaged() throws Exception {
        // Arrange: Solo usamos el MockBean que ya declaraste arriba
        Customer customer = new Customer();
        customer.setNombres("Carlos");
        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer));

        // Configuramos el Mock del SERVICIO (no del repositorio)
        when(customerServicePort.findAll(any(Pageable.class))).thenReturn(customerPage);

        // Act & Assert: Simulamos la petición HTTP
        mockMvc.perform(get("/api/v1/customers")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificamos que el controlador llamó al servicio
        verify(customerServicePort, times(1)).findAll(any(Pageable.class));
    }
}
