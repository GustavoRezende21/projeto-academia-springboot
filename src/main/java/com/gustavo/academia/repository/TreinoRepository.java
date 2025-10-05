package com.gustavo.academia.repository;

import com.gustavo.academia.entity.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
}