package com.fintransfer.service;

import com.fintransfer.model.Transfer;
import com.fintransfer.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransferServiceTest {

    @Autowired
    private TransferService service;

    @Test
    void shouldSaveAndRetrieveTransfer() {
        Transfer transfer = Transfer.builder()
                .sourceAccount("1234567890")
                .destinationAccount("0987654321")
                .amount(new BigDecimal("1000.00"))
                .fee(new BigDecimal("0.00"))
                .scheduledDate(LocalDate.now().plusDays(1))
                .build();

        Transfer saved = service.save(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer retrieved = service.findById(saved.getId()).orElseThrow();
        assertThat(retrieved.getAmount()).isEqualByComparingTo("1000.00");
    }
}
