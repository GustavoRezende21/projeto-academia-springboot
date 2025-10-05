package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.treino.TreinoRequestDTO;
import com.gustavo.academia.dto.treino.TreinoResponseDTO;
import com.gustavo.academia.entity.Treino;
import com.gustavo.academia.utils.NivelTreino;
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

    @Autowired
    public TreinoControllerV2(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    private TreinoResponseDTO convertToDTO(Treino treino) {
        TreinoResponseDTO dto = new TreinoResponseDTO();
        dto.setId(treino.getId());
        dto.setNome(treino.getNome());
        dto.setDescricao(treino.getDescricao());
        dto.setNivel(treino.getNivel());
        return dto;
    }

    private Treino convertToEntity(TreinoRequestDTO dto) {
        Treino treino = new Treino();
        treino.setNome(dto.getNome());
        treino.setDescricao(dto.getDescricao());
        if (dto.getNivel() != null) {
            treino.setNivel(NivelTreino.valueOf(dto.getNivel().toUpperCase()));
        }
        return treino;
    }

    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> getAllTreinos() {
        List<TreinoResponseDTO> treinosDTO = treinoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(treinosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> getTreinoById(@PathVariable Long id) {
        Optional<Treino> treino = treinoService.findById(id);
        return treino.map(this::convertToDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TreinoResponseDTO> createTreino(@RequestBody TreinoRequestDTO treinoDTO) {
        Treino treino = convertToEntity(treinoDTO);
        Treino novoTreino = treinoService.save(treino);
        return new ResponseEntity<>(convertToDTO(novoTreino), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> updateTreino(@PathVariable Long id, @RequestBody TreinoRequestDTO treinoDTO) {
        Optional<Treino> treinoOptional = treinoService.findById(id);
        if (treinoOptional.isPresent()) {
            Treino treino = treinoOptional.get();
            treino.setNome(treinoDTO.getNome());
            treino.setDescricao(treinoDTO.getDescricao());
            if (treinoDTO.getNivel() != null) {
                treino.setNivel(NivelTreino.valueOf(treinoDTO.getNivel().toUpperCase()));
            }

            Treino updatedTreino = treinoService.save(treino);
            return ResponseEntity.ok(convertToDTO(updatedTreino));
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