package com.fintransfer.repository;

import com.fintransfer.model.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransferRepositoryTest {

    @Autowired
    private TransferRepository repository;

    @Test
    void shouldSaveAndFindTransfer() {
        Transfer transfer = Transfer.builder()
                .sourceAccount("1111111111")
                .destinationAccount("2222222222")
                .amount(new BigDecimal("500.00"))
                .fee(new BigDecimal("5.00"))
                .scheduledDate(LocalDate.now().plusDays(2))
                .build();

        Transfer saved = repository.save(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer found = repository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getAmount()).isEqualByComparingTo("500.00");
        assertThat(found.getSourceAccount()).isEqualTo("1111111111");
        assertThat(found.getDestinationAccount()).isEqualTo("2222222222");
    }
}
