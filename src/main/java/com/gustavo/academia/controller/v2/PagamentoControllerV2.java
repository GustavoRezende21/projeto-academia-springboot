package com.gustavo.academia.controller.v2;

import com.gustavo.academia.dto.pagamento.PagamentoRequestDTO;
import com.gustavo.academia.dto.pagamento.PagamentoResponseDTO;
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

    @Autowired
    public PagamentoControllerV2(PagamentoService pagamentoService, AlunoService alunoService) {
        this.pagamentoService = pagamentoService;
        this.alunoService = alunoService;
    }

    private PagamentoResponseDTO convertToDTO(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();
        dto.setId(pagamento.getId());
        dto.setValor(pagamento.getValor());
        dto.setDataPagamento(pagamento.getDataPagamento());
        dto.setAlunoId(pagamento.getAluno().getId());
        return dto;
    }

    private Pagamento convertToEntity(PagamentoRequestDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.getValor());
        if (dto.getAlunoId() != null) {
            Aluno aluno = alunoService.findById(dto.getAlunoId())
                    .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
            pagamento.setAluno(aluno);
        }
        return pagamento;
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> getAllPagamentos() {
        List<PagamentoResponseDTO> pagamentosDTO = pagamentoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoById(@PathVariable Long id) {
        Optional<Pagamento> pagamento = pagamentoService.findById(id);
        return pagamento.map(this::convertToDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO) {
        try {
            Pagamento pagamento = convertToEntity(pagamentoDTO);
            Pagamento novoPagamento = pagamentoService.save(pagamento);
            return new ResponseEntity<>(convertToDTO(novoPagamento), HttpStatus.CREATED);
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
