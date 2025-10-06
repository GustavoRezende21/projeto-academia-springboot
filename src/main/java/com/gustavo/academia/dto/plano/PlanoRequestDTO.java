package com.gustavo.academia.dto.plano;

import java.math.BigDecimal;

public record PlanoRequestDTO(
        String nome,
        BigDecimal valor
) {}