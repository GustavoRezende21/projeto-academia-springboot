package com.gustavo.academia.dto.treino;

public class TreinoRequestDTO {

    private String nome;
    private String descricao;
    private String nivel; // Para ser convertido para o enum na camada de serviço

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}