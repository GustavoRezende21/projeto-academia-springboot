package com.gustavo.academia.service;

import com.gustavo.academia.entity.Treino;
import com.gustavo.academia.repository.TreinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;

    @Autowired
    public TreinoService(TreinoRepository treinoRepository) {
        this.treinoRepository = treinoRepository;
    }

    public List<Treino> findAll() {
        return treinoRepository.findAll();
    }

    public Optional<Treino> findById(Long id) {
        return treinoRepository.findById(id);
    }

    public Treino save(Treino treino) {
        return treinoRepository.save(treino);
    }

    public void deleteById(Long id) {
        Treino treino = treinoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Treino não encontrado."));
        if (!treino.getAlunos().isEmpty()) {
            throw new IllegalStateException("Este treino está associado a um ou mais alunos.");
        }
        treinoRepository.delete(treino);
    }
}