package com.gustavo.academia.dto.plano;

import java.math.BigDecimal;

public record PlanoResponseDTO(
        Long id,
        String nome,
        BigDecimal valor
) {}