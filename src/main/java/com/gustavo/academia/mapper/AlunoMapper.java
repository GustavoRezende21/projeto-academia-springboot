package com.gustavo.academia.mapper;

import com.gustavo.academia.dto.aluno.AlunoRequestDTO;
import com.gustavo.academia.dto.aluno.AlunoResponseDTO;
import com.gustavo.academia.entity.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mappings({
            @Mapping(source = "plano", target = "plano")
    })
    AlunoResponseDTO toAlunoResponseDTO(Aluno aluno);

    @Mappings({
            @Mapping(target = "plano", ignore = true),
            @Mapping(target = "pagamentos", ignore = true),
            @Mapping(target = "treinos", ignore = true)
    })
    Aluno toAluno(AlunoRequestDTO alunoDTO);
}