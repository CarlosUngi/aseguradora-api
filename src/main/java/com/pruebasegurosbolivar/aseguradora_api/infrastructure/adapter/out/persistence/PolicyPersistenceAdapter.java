package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Policy;
import com.pruebasegurosbolivar.aseguradora_api.domain.ports.out.PolicyRepositoryPort;
import com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository.JpaPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PolicyPersistenceAdapter implements PolicyRepositoryPort {

    private final JpaPolicyRepository jpaPolicyRepository;

    @Override
    public Policy save(Policy policy) {
        return jpaPolicyRepository.save(policy);
    }

    @Override
    public Optional<Policy> findById(Long id) {
        return jpaPolicyRepository.findById(id);
    }

    @Override
    public List<Policy> findAll() {
        return jpaPolicyRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaPolicyRepository.deleteById(id);
    }


}