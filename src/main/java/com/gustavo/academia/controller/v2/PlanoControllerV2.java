package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.plano.PlanoRequestDTO;
import com.gustavo.academia.dto.plano.PlanoResponseDTO;
import com.gustavo.academia.mapper.PlanoMapper;
import com.gustavo.academia.entity.Plano;
import com.gustavo.academia.service.PlanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/planos")
public class PlanoControllerV2 {

    private final PlanoService planoService;
    private final PlanoMapper planoMapper;

    @Autowired
    public PlanoControllerV2(PlanoService planoService, PlanoMapper planoMapper) {
        this.planoService = planoService;
        this.planoMapper = planoMapper;
    }

    @GetMapping
    public ResponseEntity<List<PlanoResponseDTO>> getAllPlanos() {
        List<PlanoResponseDTO> planosDTO = planoService.findAll().stream()
                .map(planoMapper::toPlanoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> getPlanoById(@PathVariable Long id) {
        Optional<Plano> plano = planoService.findById(id);
        return plano.map(planoMapper::toPlanoResponseDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlanoResponseDTO> createPlano(@RequestBody PlanoRequestDTO planoDTO) {
        Plano plano = planoMapper.toPlano(planoDTO);
        Plano novoPlano = planoService.save(plano);
        return new ResponseEntity<>(planoMapper.toPlanoResponseDTO(novoPlano), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> updatePlano(@PathVariable Long id, @RequestBody PlanoRequestDTO planoDTO) {
        Optional<Plano> planoOptional = planoService.findById(id);
        if (planoOptional.isPresent()) {
            Plano plano = planoOptional.get();
            plano.setNome(planoDTO.nome());
            plano.setValor(planoDTO.valor());

            Plano updatedPlano = planoService.save(plano);
            return ResponseEntity.ok(planoMapper.toPlanoResponseDTO(updatedPlano));
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