package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.pagamento.PagamentoRequestDTO;
import com.gustavo.academia.dto.pagamento.PagamentoResponseDTO;
import com.gustavo.academia.mapper.PagamentoMapper;
import com.gustavo.academia.entity.Pagamento;
import com.gustavo.academia.entity.Aluno;
import com.gustavo.academia.service.PagamentoService;
import com.gustavo.academia.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/pagamentos")
public class PagamentoControllerV2 {

    private final PagamentoService pagamentoService;
    private final AlunoService alunoService;
    private final PagamentoMapper pagamentoMapper;

    @Autowired
    public PagamentoControllerV2(PagamentoService pagamentoService, AlunoService alunoService, PagamentoMapper pagamentoMapper) {
        this.pagamentoService = pagamentoService;
        this.alunoService = alunoService;
        this.pagamentoMapper = pagamentoMapper;
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> getAllPagamentos() {
        List<PagamentoResponseDTO> pagamentosDTO = pagamentoService.findAll().stream()
                .map(pagamentoMapper::toPagamentoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoById(@PathVariable Long id) {
        Optional<Pagamento> pagamento = pagamentoService.findById(id);
        return pagamento.map(pagamentoMapper::toPagamentoResponseDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO) {
        try {
            Pagamento pagamento = pagamentoMapper.toPagamento(pagamentoDTO);

            Aluno aluno = alunoService.findById(pagamentoDTO.alunoId())
                    .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com o ID: " + pagamentoDTO.alunoId()));
            pagamento.setAluno(aluno);

            Pagamento novoPagamento = pagamentoService.save(pagamento);
            return new ResponseEntity<>(pagamentoMapper.toPagamentoResponseDTO(novoPagamento), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        Optional<Pagamento> pagamento = pagamentoService.findById(id);
        if (pagamento.isPresent()) {
            pagamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
