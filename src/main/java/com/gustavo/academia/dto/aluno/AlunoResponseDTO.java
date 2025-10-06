package com.gustavo.academia.dto.aluno;

import com.gustavo.academia.dto.plano.PlanoResponseDTO;
import java.time.LocalDate;

public record AlunoResponseDTO(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        LocalDate dataMatricula,
        boolean status,
        PlanoResponseDTO plano
) {}