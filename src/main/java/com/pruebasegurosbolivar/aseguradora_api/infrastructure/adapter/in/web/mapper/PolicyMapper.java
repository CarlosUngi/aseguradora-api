package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.mapper;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyCreateRequest;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.in.web.dto.PolicyResponse;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para la conversión de objetos entre la capa de presentación (DTOs) y la capa de dominio (Entidades).
 * Utiliza MapStruct para generar la implementación automáticamente.
 */
@Mapper(componentModel = "spring")
public interface PolicyMapper {

    /**
     * Convierte una solicitud de creación de póliza (DTO) a una entidad de dominio Policy.
     * Mapea los IDs de cliente y tipo de póliza a sus respectivas entidades anidadas.
     *
     * @param request El DTO con los datos de creación.
     * @return La entidad Policy.
     */
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "policyType.id", source = "policyTypeId")
    Policy toDomain(PolicyCreateRequest request);

    /**
     * Convierte una entidad de dominio Policy a un DTO de respuesta.
     * Aplana ciertas estructuras para facilitar el consumo por parte del cliente,
     * extrayendo nombres e IDs de las relaciones.
     *
     * @param policy La entidad de dominio.
     * @return El DTO de respuesta.
     */
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.nombres")
    @Mapping(target = "policyTypeId", source = "policyType.id")
    @Mapping(target = "policyTypeName", source = "policyType.nombre")
    PolicyResponse toResponse(Policy policy);

    /**
     * Convierte una lista de entidades Policy a una lista de DTOs de respuesta.
     *
     * @param policies Lista de entidades.
     * @return Lista de DTOs.
     */
    List<PolicyResponse> toResponseList(List<Policy> policies);

    /**
     * Método de post-procesamiento para vincular relaciones bidireccionales.
     * Se asegura de que los beneficiarios tengan la referencia correcta a la póliza padre
     * después del mapeo inicial.
     *
     * @param policy La entidad Policy parcialmente mapeada.
     */
    @AfterMapping
    default void linkRelationships(@MappingTarget Policy policy) {
        if (policy.getBeneficiaries() != null) {
            policy.getBeneficiaries().forEach(b -> b.setPolicy(policy));
        }
    }
}