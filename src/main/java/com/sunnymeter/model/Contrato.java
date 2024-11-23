package com.sunnymeter.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID contratoUuid;

    @Column(nullable = false)
    private UUID clienteUuid;

    @Column(nullable = false)
    private UUID instalacaoUuid;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDateTime horaCriacao; // Adicionando LocalDateTime para armazenar a criação do contrato

    @Column(nullable = false)
    private int duracaoContrato;

    @Column(nullable = false)
    private String status;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getContratoUuid() {
        return contratoUuid;
    }

    public void setContratoUuid(UUID contratoUuid) {
        this.contratoUuid = contratoUuid;
    }

    public UUID getClienteUuid() {
        return clienteUuid;
    }

    public void setClienteUuid(UUID clienteUuid) {
        this.clienteUuid = clienteUuid;
    }

    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public void setInstalacaoUuid(UUID instalacaoUuid) {
        this.instalacaoUuid = instalacaoUuid;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(LocalDateTime horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public int getDuracaoContrato() {
        return duracaoContrato;
    }

    public void setDuracaoContrato(int duracaoContrato) {
        this.duracaoContrato = duracaoContrato;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
