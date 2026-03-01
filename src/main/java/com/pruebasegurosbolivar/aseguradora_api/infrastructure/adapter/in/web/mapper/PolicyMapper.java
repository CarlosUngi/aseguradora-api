package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "policyType.id", source = "policyTypeId")
    @Mapping(target = "beneficiaries", source = "beneficiaries")
    @Mapping(target = "vehicles", source = "vehicles")
    Policy toDomain(PolicyCreateRequest request);

    @AfterMapping
    default void linkRelationships(@MappingTarget Policy policy) {
        // Sincroniza Beneficiarios
        if (policy.getBeneficiaries() != null) {
            policy.getBeneficiaries().forEach(b -> b.setPolicy(policy));
        }
        // Sincroniza Vehículos (Muchos a Muchos)
        if (policy.getVehicles() != null) {
            policy.getVehicles().forEach(v -> {
                if (v.getPolicies() == null) v.setPolicies(new java.util.ArrayList<>());
                if (!v.getPolicies().contains(policy)) v.getPolicies().add(policy);
            });
        }
    }
}