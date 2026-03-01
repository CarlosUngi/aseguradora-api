package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.CustomerUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Mapea el DTO de creación a la entidad de dominio.
     */
    Customer toDomain(CustomerCreateRequest request);

    /**
     * Actualiza una instancia existente de Customer con los datos del DTO.
     * Ignora los campos nulos en el request para evitar sobreescritura accidental.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(CustomerUpdateRequest request, @MappingTarget Customer customer);
}