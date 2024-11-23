package com.sunnymeter.dto;

import com.sunnymeter.model.Cliente;

public class ClienteDTO {

    private String clienteUuid;
    private String nome;
    private String endereco;
    private String documento;
    private String tipo;
    private String cep;
    private boolean ativo;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.clienteUuid = cliente.getClienteUuid();
        this.nome = cliente.getNome();
        this.endereco = cliente.getEndereco();
        this.documento = cliente.getDocumento();
        this.tipo = cliente.getTipo();
        this.cep = cliente.getCep();
        this.ativo = cliente.isAtivo();
    }

    // Getters e Setters
    public String getClienteUuid() {
        return clienteUuid;
    }

    public void setClienteUuid(String clienteUuid) {
        this.clienteUuid = clienteUuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
