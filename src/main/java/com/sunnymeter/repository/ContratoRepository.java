package com.sunnymeter.repository;

import com.sunnymeter.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContratoRepository extends JpaRepository<Contrato, UUID> {
    Optional<Contrato> findByContratoUuid(UUID contratoUuid);
}
