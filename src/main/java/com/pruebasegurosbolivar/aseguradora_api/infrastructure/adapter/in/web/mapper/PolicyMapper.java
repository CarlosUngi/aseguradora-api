package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyResponse;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    // Mapeo de Entrada (Request -> Entity)
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "policyType.id", source = "policyTypeId")
    Policy toDomain(PolicyCreateRequest request);

    // Mapeo de Salida (Entity -> Response) - ESTO SOLUCIONA EL BUCLE
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.nombres")
    @Mapping(target = "policyTypeId", source = "policyType.id")
    @Mapping(target = "policyTypeName", source = "policyType.nombre")
    PolicyResponse toResponse(Policy policy);

    List<PolicyResponse> toResponseList(List<Policy> policies);

    @AfterMapping
    default void linkRelationships(@MappingTarget Policy policy) {
        if (policy.getBeneficiaries() != null) {
            policy.getBeneficiaries().forEach(b -> b.setPolicy(policy));
        }
    }
}