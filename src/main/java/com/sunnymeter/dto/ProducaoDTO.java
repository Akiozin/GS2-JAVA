package com.sunnymeter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProducaoDTO {
    @JsonProperty("registro_producao_uuid")
    private String registroProducaoUuid;

    @JsonProperty("instalacao_uuid")
    private String instalacaoUuid;

    @JsonProperty("producao_kwh")
    private double producaoKwh;

    @JsonProperty("medicao_timestamp")
    private long medicaoTimestamp;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("medicao_timestamp_horario")
    private String medicaoTimestampHorario;

    @JsonProperty("created_at_horario")
    private String createdAtHorario;

    public ProducaoDTO(String registroProducaoUuid, String instalacaoUuid, double producaoKwh, long medicaoTimestamp, long createdAt, String medicaoTimestampHorario, String createdAtHorario) {
        this.registroProducaoUuid = registroProducaoUuid;
        this.instalacaoUuid = instalacaoUuid;
        this.producaoKwh = producaoKwh;
        this.medicaoTimestamp = medicaoTimestamp;
        this.createdAt = createdAt;
        this.medicaoTimestampHorario = medicaoTimestampHorario;
        this.createdAtHorario = createdAtHorario;
    }

    // Getters e Setters
    public String getRegistroProducaoUuid() {
        return registroProducaoUuid;
    }

    public void setRegistroProducaoUuid(String registroProducaoUuid) {
        this.registroProducaoUuid = registroProducaoUuid;
    }

    public String getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public void setInstalacaoUuid(String instalacaoUuid) {
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

    public String getMedicaoTimestampHorario() {
        return medicaoTimestampHorario;
    }

    public void setMedicaoTimestampHorario(String medicaoTimestampHorario) {
        this.medicaoTimestampHorario = medicaoTimestampHorario;
    }

    public String getCreatedAtHorario() {
        return createdAtHorario;
    }

    public void setCreatedAtHorario(String createdAtHorario) {
        this.createdAtHorario = createdAtHorario;
    }
}
