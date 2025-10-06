package com.gustavo.academia.dto.treino;

import com.gustavo.academia.utils.NivelTreino;

public record TreinoResponseDTO(
        Long id,
        String nome,
        String descricao,
        NivelTreino nivel
) {}