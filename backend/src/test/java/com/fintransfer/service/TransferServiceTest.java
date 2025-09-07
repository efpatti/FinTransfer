package com.fintransfer.service;

import com.fintransfer.model.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class TransferServiceTest {

    @Autowired
    private TransferService service;

    @Test
    void shouldSaveAndRetrieveTransfer() {
        Transfer transfer = Transfer.builder()
                .originAccount("1234567890") // 10 chars
                .destinationAccount("0987654321") // 10 chars
                .transferAmount(new BigDecimal("1000.00"))
                .fee(new BigDecimal("0.00"))
                .transferDate(LocalDate.now().plusDays(1))  // quando a transferência vai ocorrer
                .scheduleDate(LocalDate.now())             // hoje
                .status("PENDING")
                .build();

        Transfer saved = service.createTransfer(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer retrieved = service.findById(saved.getId()).orElseThrow();
        assertThat(retrieved.getTransferAmount()).isEqualByComparingTo("1000.00");
        assertThat(retrieved.getOriginAccount()).isEqualTo("1234567890");
        assertThat(retrieved.getDestinationAccount()).isEqualTo("0987654321");
        assertThat(retrieved.getStatus()).isEqualTo("PENDING");
    }

    @Test
    void shouldRejectZeroAmount() {
        Transfer transfer = Transfer.builder()
                .originAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(BigDecimal.ZERO)
                .fee(BigDecimal.ZERO)
                .transferDate(LocalDate.now().plusDays(1))
                .scheduleDate(LocalDate.now())
                .status("PENDING")
                .build();
        try {
            service.createTransfer(transfer);
            fail("Should throw IllegalArgumentException for zero amount");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("amount");
        }
    }

    @Test
    void shouldRejectPastScheduleDate() {
        Transfer transfer = Transfer.builder()
                .originAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .fee(BigDecimal.ZERO)
                .transferDate(LocalDate.now().plusDays(1))
                .scheduleDate(LocalDate.now().minusDays(1))
                .status("PENDING")
                .build();
        try {
            service.createTransfer(transfer);
            fail("Should throw IllegalArgumentException for past schedule date");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Schedule date cannot be in the past.");
        }
    }

    @Test
    void shouldCalculateFeeCorrectly() {
        Transfer transfer = Transfer.builder()
                .originAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("1000.00"))
                .fee(BigDecimal.ZERO)
                .transferDate(LocalDate.now().plusDays(10))
                .scheduleDate(LocalDate.now())
                .status("PENDING")
                .build();
        Transfer saved = service.createTransfer(transfer);
        assertThat(saved.getFee()).isNotNull();
        // Add more assertions for expected fee value if business logic is known
    }
}
