package com.gustavo.academia.dto.pagamento;

import java.math.BigDecimal;

public class PagamentoRequestDTO {

    private BigDecimal valor;
    private Long alunoId;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }
}
