package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.aluno.AlunoRequestDTO;
import com.gustavo.academia.dto.aluno.AlunoResponseDTO;
import com.gustavo.academia.dto.plano.PlanoResponseDTO;
import com.gustavo.academia.entity.Aluno;
import com.gustavo.academia.entity.Plano;
import com.gustavo.academia.service.AlunoService;
import com.gustavo.academia.service.PlanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/alunos")
public class AlunoControllerV2 {

    private final AlunoService alunoService;
    private final PlanoService planoService;

    @Autowired
    public AlunoControllerV2(AlunoService alunoService, PlanoService planoService) {
        this.alunoService = alunoService;
        this.planoService = planoService;
    }

    private AlunoResponseDTO convertToDTO(Aluno aluno) {
        AlunoResponseDTO dto = new AlunoResponseDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setCpf(aluno.getCpf());
        dto.setDataNascimento(aluno.getDataNascimento());
        dto.setDataMatricula(aluno.getDataMatricula());
        dto.setStatus(aluno.isStatus());
        if (aluno.getPlano() != null) {
            dto.setPlano(convertToPlanoDTO(aluno.getPlano()));
        }
        return dto;
    }

    private PlanoResponseDTO convertToPlanoDTO(Plano plano) {
        PlanoResponseDTO dto = new PlanoResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setValor(plano.getValor());
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> getAllAlunos() {
        List<AlunoResponseDTO> alunosDTO = alunoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> getAlunoById(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.findById(id);
        return aluno.map(this::convertToDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> createAluno(@RequestBody AlunoRequestDTO alunoDTO) {
        try {
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setCpf(alunoDTO.getCpf());
            aluno.setDataNascimento(alunoDTO.getDataNascimento());
            aluno.setDataMatricula(alunoDTO.getDataMatricula());
            aluno.setStatus(alunoDTO.isStatus());

            if (alunoDTO.getPlanoId() != null) {
                Plano plano = planoService.findById(alunoDTO.getPlanoId())
                        .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado."));
                aluno.setPlano(plano);
            }

            Aluno novoAluno = alunoService.save(aluno);
            return new ResponseEntity<>(convertToDTO(novoAluno), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoRequestDTO alunoDTO) {
        Optional<Aluno> alunoOptional = alunoService.findById(id);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            aluno.setNome(alunoDTO.getNome());
            aluno.setCpf(alunoDTO.getCpf());
            aluno.setDataNascimento(alunoDTO.getDataNascimento());
            aluno.setDataMatricula(alunoDTO.getDataMatricula());
            aluno.setStatus(alunoDTO.isStatus());

            if (alunoDTO.getPlanoId() != null) {
                Plano plano = planoService.findById(alunoDTO.getPlanoId())
                        .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado."));
                aluno.setPlano(plano);
            }

            Aluno updatedAluno = alunoService.save(aluno);
            return ResponseEntity.ok(convertToDTO(updatedAluno));
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