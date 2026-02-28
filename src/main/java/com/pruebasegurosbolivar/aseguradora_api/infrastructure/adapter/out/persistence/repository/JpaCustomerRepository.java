package com.pruebasegurosbolivar.aseguradora_api.infrastructure.adapter.out.persistence.repository;

import com.pruebasegurosbolivar.aseguradora_api.domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {
}
