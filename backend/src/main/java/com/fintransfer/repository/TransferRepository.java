package com.fintransfer.repository;

import com.fintransfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    
    // Encontrar todos os transfers (já vem do JpaRepository)
    
    // Encontrar por intervalo de datas (opcional)
    List<Transfer> findByScheduleDateBetween(LocalDate start, LocalDate end);
}
