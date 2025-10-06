package com.gustavo.academia.mapper;

import com.gustavo.academia.dto.treino.TreinoRequestDTO;
import com.gustavo.academia.dto.treino.TreinoResponseDTO;
import com.gustavo.academia.entity.Treino;
import com.gustavo.academia.utils.NivelTreino;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TreinoMapper {

    TreinoResponseDTO toTreinoResponseDTO(Treino treino);

    Treino toTreino(TreinoRequestDTO treinoDTO);

    default NivelTreino stringToNivelTreino(String nivel) {
        return NivelTreino.valueOf(nivel.toUpperCase());
    }
}