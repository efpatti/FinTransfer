package com.fintransfer.repository;

import com.fintransfer.model.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransferRepositoryQueryTest {
    @Autowired
    private TransferRepository repository;

    @Test
    void shouldFindTransfersByScheduleDateRange() {
        Transfer t1 = Transfer.builder()
            .originAccount("1111111111")
            .destinationAccount("2222222222")
            .transferAmount(new BigDecimal("100.00"))
            .fee(new BigDecimal("5.00"))
            .transferDate(LocalDate.now().plusDays(1))
            .scheduleDate(LocalDate.now())
            .status("PENDING")
            .build();
        Transfer t2 = Transfer.builder()
            .originAccount("3333333333")
            .destinationAccount("4444444444")
            .transferAmount(new BigDecimal("200.00"))
            .fee(new BigDecimal("10.00"))
            .transferDate(LocalDate.now().plusDays(2))
            .scheduleDate(LocalDate.now().plusDays(1))
            .status("PENDING")
            .build();
        repository.save(t1);
        repository.save(t2);
        List<Transfer> found = repository.findByScheduleDateBetween(LocalDate.now(), LocalDate.now().plusDays(1));
        assertThat(found).hasSize(2);
    }
}
