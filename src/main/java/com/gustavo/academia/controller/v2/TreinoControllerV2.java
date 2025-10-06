package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.treino.TreinoRequestDTO;
import com.gustavo.academia.dto.treino.TreinoResponseDTO;
import com.gustavo.academia.mapper.TreinoMapper;
import com.gustavo.academia.entity.Treino;
import com.gustavo.academia.service.TreinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/treinos")
public class TreinoControllerV2 {

    private final TreinoService treinoService;
    private final TreinoMapper treinoMapper;

    @Autowired
    public TreinoControllerV2(TreinoService treinoService, TreinoMapper treinoMapper) {
        this.treinoService = treinoService;
        this.treinoMapper = treinoMapper;
    }

    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> getAllTreinos() {
        List<TreinoResponseDTO> treinosDTO = treinoService.findAll().stream()
                .map(treinoMapper::toTreinoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(treinosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> getTreinoById(@PathVariable Long id) {
        Optional<Treino> treino = treinoService.findById(id);
        return treino.map(treinoMapper::toTreinoResponseDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TreinoResponseDTO> createTreino(@RequestBody TreinoRequestDTO treinoDTO) {
        Treino treino = treinoMapper.toTreino(treinoDTO);
        Treino novoTreino = treinoService.save(treino);
        return new ResponseEntity<>(treinoMapper.toTreinoResponseDTO(novoTreino), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> updateTreino(@PathVariable Long id, @RequestBody TreinoRequestDTO treinoDTO) {
        Optional<Treino> treinoOptional = treinoService.findById(id);
        if (treinoOptional.isPresent()) {
            Treino treino = treinoOptional.get();
            treino.setNome(treinoDTO.nome());
            treino.setDescricao(treinoDTO.descricao());
            treino.setNivel(treinoMapper.stringToNivelTreino(treinoDTO.nivel()));

            Treino updatedTreino = treinoService.save(treino);
            return ResponseEntity.ok(treinoMapper.toTreinoResponseDTO(updatedTreino));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreino(@PathVariable Long id) {
        try {
            treinoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}