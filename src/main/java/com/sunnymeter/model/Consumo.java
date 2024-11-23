package com.sunnymeter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID consumoUuid;

    @Column(nullable = false)
    private UUID instalacaoUuid;

    @Column(nullable = false)
    private double consumoKwh;

    @Column(nullable = false)
    private long medicaoTimestamp;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Getters e Setters
    public UUID getConsumoUuid() {
        return consumoUuid;
    }

    public void setConsumoUuid(UUID consumoUuid) {
        this.consumoUuid = consumoUuid;
    }

    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public void setInstalacaoUuid(UUID instalacaoUuid) {
        this.instalacaoUuid = instalacaoUuid;
    }

    public double getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public long getMedicaoTimestamp() {
        return medicaoTimestamp;
    }

    public void setMedicaoTimestamp(long medicaoTimestamp) {
        this.medicaoTimestamp = medicaoTimestamp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
