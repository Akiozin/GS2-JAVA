package com.sunnymeter.repository;

import com.sunnymeter.model.Instalacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InstalacaoRepository extends JpaRepository<Instalacao, UUID> {
    Optional<Instalacao> findByInstalacaoUuid(UUID instalacaoUuid);
}
