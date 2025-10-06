package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.aluno.AlunoRequestDTO;
import com.gustavo.academia.dto.aluno.AlunoResponseDTO;
import com.gustavo.academia.mapper.AlunoMapper;
import com.gustavo.academia.entity.Aluno;
import com.gustavo.academia.entity.Plano;
import com.gustavo.academia.service.AlunoService;
import com.gustavo.academia.service.PlanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/alunos")
public class AlunoControllerV2 {

    private final AlunoService alunoService;
    private final PlanoService planoService;
    private final AlunoMapper alunoMapper;

    @Autowired
    public AlunoControllerV2(AlunoService alunoService, PlanoService planoService, AlunoMapper alunoMapper) {
        this.alunoService = alunoService;
        this.planoService = planoService;
        this.alunoMapper = alunoMapper;
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> getAllAlunos() {
        List<AlunoResponseDTO> alunosDTO = alunoService.findAll().stream()
                .map(alunoMapper::toAlunoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> getAlunoById(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.findById(id);
        return aluno.map(alunoMapper::toAlunoResponseDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> createAluno(@RequestBody AlunoRequestDTO alunoDTO) {
        Aluno aluno = alunoMapper.toAluno(alunoDTO);

        if (alunoDTO.planoId() != null) {
            Optional<Plano> planoOptional = planoService.findById(alunoDTO.planoId());
            planoOptional.ifPresent(aluno::setPlano);
        }
        Aluno novoAluno = alunoService.save(aluno);
        return new ResponseEntity<>(alunoMapper.toAlunoResponseDTO(novoAluno), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoRequestDTO alunoDTO) {
        Optional<Aluno> alunoOptional = alunoService.findById(id);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            aluno.setNome(alunoDTO.nome());
            aluno.setCpf(alunoDTO.cpf());
            aluno.setDataNascimento(alunoDTO.dataNascimento());
            aluno.setDataMatricula(alunoDTO.dataMatricula());
            aluno.setStatus(alunoDTO.status());

            if (alunoDTO.planoId() != null) {
                Optional<Plano> planoOptional = planoService.findById(alunoDTO.planoId());
                planoOptional.ifPresent(aluno::setPlano);
            } else {
                aluno.setPlano(null);
            }

            Aluno updatedAluno = alunoService.save(aluno);
            return ResponseEntity.ok(alunoMapper.toAlunoResponseDTO(updatedAluno));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.findById(id);
        if (aluno.isPresent()) {
            alunoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}