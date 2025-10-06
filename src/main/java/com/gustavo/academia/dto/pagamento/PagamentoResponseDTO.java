package com.gustavo.academia.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
        Long id,
        BigDecimal valor,
        LocalDate dataPagamento,
        Long alunoId
) {}