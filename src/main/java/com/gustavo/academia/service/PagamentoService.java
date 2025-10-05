package com.gustavo.academia.service;

import com.gustavo.academia.entity.Pagamento;
import com.gustavo.academia.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> findById(Long id) {
        return pagamentoRepository.findById(id);
    }

    public Pagamento save(Pagamento pagamento) {
        pagamento.setDataPagamento(LocalDate.now());
        return pagamentoRepository.save(pagamento);
    }

    public void deleteById(Long id) {
        pagamentoRepository.deleteById(id);
    }
}