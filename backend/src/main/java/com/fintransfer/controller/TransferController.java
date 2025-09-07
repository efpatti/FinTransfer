package com.fintransfer.controller;

import com.fintransfer.model.Transfer;
import com.fintransfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelTransfer(@PathVariable Long id) {
        try {
            Transfer cancelled = transferService.cancelTransfer(id);
            return ResponseEntity.ok(cancelled);
        } catch (javax.persistence.EntityNotFoundException ex) {
            // Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            // Cannot cancel
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<?> scheduleTransfer(@Valid @RequestBody Transfer transfer) {
        try {
            Transfer savedTransfer = transferService.createTransfer(transfer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransfer);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
        @GetMapping
        public ResponseEntity<?> listTransfers(
                @RequestParam(required = false) String startDate,
                @RequestParam(required = false) String endDate) {
            try {
                if (startDate != null && endDate != null) {
                    // Parse dates and filter
                    java.time.LocalDate start = java.time.LocalDate.parse(startDate);
                    java.time.LocalDate end = java.time.LocalDate.parse(endDate);
                    return ResponseEntity.ok(transferService.findByScheduleDateBetween(start, end));
                } else {
                    return ResponseEntity.ok(transferService.findAllTransfers());
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
            }
        }
}
