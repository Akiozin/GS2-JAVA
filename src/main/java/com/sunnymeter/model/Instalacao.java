package com.sunnymeter.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Instalacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID instalacaoUuid;

    private String endereco;

    private String cep;

    private boolean ativo = true;

    // Getters e Setters
    public UUID getInstalacaoUuid() {
        return instalacaoUuid;
    }

    public void setInstalacaoUuid(UUID instalacaoUuid) {
        this.instalacaoUuid = instalacaoUuid;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
