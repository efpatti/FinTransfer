package com.fintransfer.service;

import com.fintransfer.model.Transfer;
import com.fintransfer.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public List<Transfer> findAll() {
        return repository.findAll();
    }

    public Transfer save(Transfer transfer) {
        return repository.save(transfer);
    }

    public Optional<Transfer> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
