package com.fintransfer.service;

import com.fintransfer.model.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import com.fintransfer.model.TransferStatus;

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
                .transferDate(LocalDate.now().plusDays(1))
                .scheduleDate(LocalDate.now())
                .status(TransferStatus.PENDING)
                .build();

        Transfer saved = service.createTransfer(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer retrieved = service.findById(saved.getId()).orElseThrow();
        assertThat(retrieved.getTransferAmount()).isEqualByComparingTo("1000.00");
        assertThat(retrieved.getOriginAccount()).isEqualTo("1234567890");
        assertThat(retrieved.getDestinationAccount()).isEqualTo("0987654321");
        assertThat(retrieved.getStatus()).isEqualTo(TransferStatus.PENDING);
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
        .status(TransferStatus.PENDING)
        .build();
    assertThatThrownBy(() -> service.createTransfer(transfer))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("amount");
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
        .status(TransferStatus.PENDING)
        .build();
    assertThatThrownBy(() -> service.createTransfer(transfer))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Schedule date cannot be in the past.");
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
        .status(TransferStatus.PENDING)
        .build();
    Transfer saved = service.createTransfer(transfer);
    assertThat(saved.getFee()).isNotNull();
    // Add more assertions for expected fee value if business logic is known
    }

    @Test
    void shouldCancelPendingTransfer() {
    Transfer transfer = Transfer.builder()
        .originAccount("1234567890")
        .destinationAccount("0987654321")
        .transferAmount(new BigDecimal("100.00"))
        .fee(BigDecimal.ZERO)
        .transferDate(LocalDate.now().plusDays(1))
        .scheduleDate(LocalDate.now())
        .status(TransferStatus.PENDING)
        .build();
    Transfer saved = service.createTransfer(transfer);
    Transfer cancelled = service.cancelTransfer(saved.getId());
    assertThat(cancelled.getStatus()).isEqualTo(TransferStatus.CANCELLED);
    }

    @Test
    void shouldNotCancelCompletedTransfer() {
    Transfer transfer = Transfer.builder()
        .originAccount("1234567890")
        .destinationAccount("0987654321")
        .transferAmount(new BigDecimal("100.00"))
        .fee(BigDecimal.ZERO)
        .transferDate(LocalDate.now().plusDays(1))
        .scheduleDate(LocalDate.now())
        .status(TransferStatus.COMPLETED)
        .build();
    Transfer saved = service.createTransfer(transfer);
    assertThatThrownBy(() -> service.cancelTransfer(saved.getId()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Cannot cancel a COMPLETED transfer");
    }

    @Test
    void shouldNotCancelAlreadyCancelledTransfer() {
    Transfer transfer = Transfer.builder()
        .originAccount("1234567890")
        .destinationAccount("0987654321")
        .transferAmount(new BigDecimal("100.00"))
        .fee(BigDecimal.ZERO)
        .transferDate(LocalDate.now().plusDays(1))
        .scheduleDate(LocalDate.now())
        .status(TransferStatus.CANCELLED)
        .build();
    Transfer saved = service.createTransfer(transfer);
    assertThatThrownBy(() -> service.cancelTransfer(saved.getId()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("already CANCELLED");
    }
}
