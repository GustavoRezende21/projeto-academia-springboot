package com.gustavo.academia.controller.v1;

import com.gustavo.academia.entity.Treino;
import com.gustavo.academia.service.TreinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/treinos")
public class TreinoControllerV1 {

    private final TreinoService treinoService;

    @Autowired
    public TreinoControllerV1(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @GetMapping
    public ResponseEntity<List<Treino>> getAllTreinos() {
        List<Treino> treinos = treinoService.findAll();
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> getTreinoById(@PathVariable Long id) {
        Optional<Treino> treino = treinoService.findById(id);
        return treino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Treino> createTreino(@RequestBody Treino treino) {
        Treino novoTreino = treinoService.save(treino);
        return new ResponseEntity<>(novoTreino, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> updateTreino(@PathVariable Long id, @RequestBody Treino treinoDetails) {
        Optional<Treino> treinoOptional = treinoService.findById(id);
        if (treinoOptional.isPresent()) {
            Treino treino = treinoOptional.get();
            treino.setNome(treinoDetails.getNome());
            treino.setDescricao(treinoDetails.getDescricao());

            Treino updatedTreino = treinoService.save(treino);
            return ResponseEntity.ok(updatedTreino);
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