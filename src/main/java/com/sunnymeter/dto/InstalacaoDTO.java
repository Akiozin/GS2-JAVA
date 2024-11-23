package com.sunnymeter.dto;

import com.sunnymeter.model.Instalacao;

import java.util.UUID;

public class InstalacaoDTO {

    private UUID instalacaoUuid;
    private String endereco;
    private String cep;
    private boolean ativo;

    public InstalacaoDTO(Instalacao instalacao) {
        this.instalacaoUuid = instalacao.getInstalacaoUuid();
        this.endereco = instalacao.getEndereco();
        this.cep = instalacao.getCep();
        this.ativo = instalacao.isAtivo();
    }

    // Getters
    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCep() {
        return cep;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
