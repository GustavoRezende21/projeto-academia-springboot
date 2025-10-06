package com.gustavo.academia.mapper;

import com.gustavo.academia.dto.plano.PlanoRequestDTO;
import com.gustavo.academia.dto.plano.PlanoResponseDTO;
import com.gustavo.academia.entity.Plano;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanoMapper {

    PlanoResponseDTO toPlanoResponseDTO(Plano plano);

    Plano toPlano(PlanoRequestDTO planoDTO);
}
