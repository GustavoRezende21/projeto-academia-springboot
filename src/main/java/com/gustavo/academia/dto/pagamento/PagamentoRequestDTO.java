package com.gustavo.academia.dto.pagamento;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
        BigDecimal valor,
        Long alunoId
) {}