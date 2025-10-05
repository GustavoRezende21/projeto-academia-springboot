package com.gustavo.academia.controller.v1;

import com.gustavo.academia.entity.Plano;
import com.gustavo.academia.service.PlanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/planos")
public class PlanoControllerV1 {

    private final PlanoService planoService;

    @Autowired
    public PlanoControllerV1(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping
    public ResponseEntity<List<Plano>> getAllPlanos() {
        List<Plano> planos = planoService.findAll();
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> getPlanoById(@PathVariable Long id) {
        Optional<Plano> plano = planoService.findById(id);
        return plano.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plano> createPlano(@RequestBody Plano plano) {
        Plano novoPlano = planoService.save(plano);
        return new ResponseEntity<>(novoPlano, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plano> updatePlano(@PathVariable Long id, @RequestBody Plano planoDetails) {
        Optional<Plano> planoOptional = planoService.findById(id);
        if (planoOptional.isPresent()) {
            Plano plano = planoOptional.get();
            plano.setNome(planoDetails.getNome());
            plano.setValor(planoDetails.getValor());

            Plano updatedPlano = planoService.save(plano);
            return ResponseEntity.ok(updatedPlano);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlano(@PathVariable Long id) {
        Optional<Plano> plano = planoService.findById(id);
        if (plano.isPresent()) {
            planoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}