package com.gustavo.academia.mapper;

import com.gustavo.academia.dto.pagamento.PagamentoRequestDTO;
import com.gustavo.academia.dto.pagamento.PagamentoResponseDTO;
import com.gustavo.academia.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    @Mappings({
            @Mapping(source = "aluno.id", target = "alunoId")
    })
    PagamentoResponseDTO toPagamentoResponseDTO(Pagamento pagamento);

    @Mappings({
            @Mapping(target = "dataPagamento", ignore = true),
            @Mapping(target = "aluno", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Pagamento toPagamento(PagamentoRequestDTO pagamentoDTO);
}