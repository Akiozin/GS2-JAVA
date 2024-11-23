package com.sunnymeter.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ContratoDTO {
    private UUID contratoUuid;
    private UUID clienteUuid;
    private UUID instalacaoUuid;
    private LocalDate dataInicio;
    private int duracaoContrato;
    private String status;
    private List<String> errors;

    public ContratoDTO(UUID contratoUuid, UUID clienteUuid, UUID instalacaoUuid, LocalDate dataInicio, int duracaoContrato, String status) {
        this.contratoUuid = contratoUuid;
        this.clienteUuid = clienteUuid;
        this.instalacaoUuid = instalacaoUuid;
        this.dataInicio = dataInicio;
        this.duracaoContrato = duracaoContrato;
        this.status = status;
    }

    public ContratoDTO(List<String> errors) {
        this.errors = errors;
    }

    // Getters e Setters
    public UUID getContratoUuid() {
        return contratoUuid;
    }

    public UUID getClienteUuid() {
        return clienteUuid;
    }

    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public int getDuracaoContrato() {
        return duracaoContrato;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
