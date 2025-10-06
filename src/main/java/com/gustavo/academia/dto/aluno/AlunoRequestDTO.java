package com.gustavo.academia.dto.aluno;

import java.time.LocalDate;

public record AlunoRequestDTO(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        LocalDate dataMatricula,
        boolean status,
        Long planoId
) {}