package com.pruebasegurosbolivar.aseguradora_api.domain.ports.out;

import java.util.List;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Beneficiary;

/**
 * Puerto de salida para la persistencia de beneficiarios.
 * Define las operaciones necesarias para interactuar con el repositorio de datos de beneficiarios.
 */
public interface  BeneficiaryRepositoryPort {

    /**
     * Busca todos los beneficiarios asociados a una póliza específica.
     *
     * @param policyId El identificador de la póliza.
     * @return Una lista de beneficiarios asociados a la póliza.
     */
    List<Beneficiary> findBeneficiaryByPolicyId(Long policyId);

    /**
     * Guarda o actualiza la información de un beneficiario.
     *
     * @param beneficiary La entidad del beneficiario a persistir.
     * @return El beneficiario persistido.
     */
    Beneficiary save(Beneficiary beneficiary);  

    
}
