package com.sunnymeter.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID registroProducaoUuid;

    private UUID instalacaoUuid;

    private double producaoKwh;

    private long medicaoTimestamp;

    private long createdAt;

    // Getters e Setters
    public UUID getRegistroProducaoUuid() {
        return registroProducaoUuid;
    }

    public void setRegistroProducaoUuid(UUID registroProducaoUuid) {
        this.registroProducaoUuid = registroProducaoUuid;
    }

    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public void setInstalacaoUuid(UUID instalacaoUuid) {
        this.instalacaoUuid = instalacaoUuid;
    }

    public double getProducaoKwh() {
        return producaoKwh;
    }

    public void setProducaoKwh(double producaoKwh) {
        this.producaoKwh = producaoKwh;
    }

    public long getMedicaoTimestamp() {
        return medicaoTimestamp;
    }

    public void setMedicaoTimestamp(long medicaoTimestamp) {
        this.medicaoTimestamp = medicaoTimestamp;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
