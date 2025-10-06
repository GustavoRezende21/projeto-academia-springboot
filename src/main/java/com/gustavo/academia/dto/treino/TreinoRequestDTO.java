package com.gustavo.academia.dto.treino;

public record TreinoRequestDTO(
        String nome,
        String descricao,
        String nivel
) {}