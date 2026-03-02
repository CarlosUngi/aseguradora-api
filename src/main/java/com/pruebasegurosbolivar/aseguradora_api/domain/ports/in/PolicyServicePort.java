package com.pruebasegurosbolivar.aseguradora_api.domain.ports.in;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;
import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import java.util.List;

/**
 * Puerto de entrada para la gestión de pólizas y sus reglas de negocio.
 * Define los contratos para la creación y consulta de pólizas, así como la gestión de beneficiarios.
 */
public interface PolicyServicePort {

    /**
     * Crea una nueva póliza en el sistema aplicando las reglas de negocio correspondientes.
     *
     * @param policy La entidad de la póliza a crear.
     * @return La póliza creada con su ID generado.
     */
    Policy createPolicy(Policy policy);

    /**
     * Busca todas las pólizas asociadas a un cliente específico.
     *
     * @param customerId El ID del cliente.
     * @return Una lista de pólizas pertenecientes al cliente.
     */
    List<Policy> findByPolicyListByClient(Long customerId);

    /**
     * Obtiene el detalle completo de una póliza por su identificador.
     *
     * @param policyId El ID de la póliza.
     * @return La entidad Policy encontrada.
     */
    Policy getPolicyDetail(Long policyId);

    /**
     * Obtiene la lista de beneficiarios asociados a una póliza específica.
     *
     * @param policyId El ID de la póliza.
     * @return Una lista de beneficiarios.
     */
    List<Beneficiary> findBeneficiaryByPolicyId(Long policyId);

    /**
     * Recupera todas las pólizas registradas en el sistema.
     *
     * @return Una lista con todas las pólizas.
     */
    List<Policy> findAllPolicies();
}
