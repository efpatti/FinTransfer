package com.fintransfer.service;

import com.fintransfer.model.Transfer;
import com.fintransfer.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository repository;
    private final FeeCalculatorService feeCalculator;

    public TransferService(TransferRepository repository, FeeCalculatorService feeCalculator) {
        this.repository = repository;
        this.feeCalculator = feeCalculator;
    }

    public List<Transfer> findAll() {
        return repository.findAll();
    }

    public Optional<Transfer> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Creates a transfer with validation and fee calculation.
     *
     * @param transfer Transfer to create
     * @return Persisted transfer with fee applied
     */
    public Transfer createTransfer(Transfer transfer) {
        if (transfer.getOriginAccount() == null || transfer.getDestinationAccount() == null) {
            throw new IllegalArgumentException("Origin and destination accounts must be provided.");
        }
        if (transfer.getTransferAmount() == null || transfer.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (transfer.getScheduleDate() == null || transfer.getTransferDate() == null) {
            throw new IllegalArgumentException("Scheduled date and transfer date must be provided.");
        }
        if (transfer.getScheduleDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Schedule date cannot be in the past.");
        }

        // Calculate fee
        transfer.setFee(feeCalculator.calculateFee(
                transfer.getTransferAmount(),
                transfer.getScheduleDate(),
                transfer.getTransferDate()
        ));

        // Persist transfer
        return repository.save(transfer);
    }

    // Removed unused save method. Use createTransfer for creation logic with validation and fee calculation.
}
