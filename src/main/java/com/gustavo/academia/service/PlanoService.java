package com.gustavo.academia.service;

import com.gustavo.academia.entity.Plano;
import com.gustavo.academia.repository.PlanoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Autowired
    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public List<Plano> findAll() {
        return planoRepository.findAll();
    }

    public Optional<Plano> findById(Long id) {
        return planoRepository.findById(id);
    }

    public Plano save(Plano plano) {
        return planoRepository.save(plano);
    }

    public void deleteById(Long id) {
        planoRepository.deleteById(id);
    }
}