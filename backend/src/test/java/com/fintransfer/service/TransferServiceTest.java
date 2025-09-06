package com.fintransfer.service;

import com.fintransfer.model.Transfer;
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
                .originAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("1000.00"))
                .fee(new BigDecimal("0.00"))
                .transferDate(LocalDate.now().plusDays(1))  // quando a transferência vai ocorrer
                .scheduleDate(LocalDate.now())             // hoje
                .status("PENDING")
                .build();

        Transfer saved = service.save(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer retrieved = service.findById(saved.getId()).orElseThrow();
        assertThat(retrieved.getTransferAmount()).isEqualByComparingTo("1000.00");
        assertThat(retrieved.getOriginAccount()).isEqualTo("1234567890");
        assertThat(retrieved.getDestinationAccount()).isEqualTo("0987654321");
        assertThat(retrieved.getStatus()).isEqualTo("PENDING");
    }
}
