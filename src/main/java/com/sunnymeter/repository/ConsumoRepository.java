package com.sunnymeter.repository;

import com.sunnymeter.model.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, UUID> {
}
