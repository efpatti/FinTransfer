package com.fintransfer.repository;

import com.fintransfer.model.TransferStatus;
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
            .originAccount("1111111111") // 10 chars
                .destinationAccount("2222222222")
                .transferAmount(new BigDecimal("500.00"))
                .fee(new BigDecimal("5.00"))
                .transferDate(LocalDate.now().plusDays(2))
                .scheduleDate(LocalDate.now())
                .status(TransferStatus.PENDING)
                .build();

        Transfer saved = repository.save(transfer);
        assertThat(saved.getId()).isNotNull();

        Transfer found = repository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTransferAmount()).isEqualByComparingTo("500.00");
        assertThat(found.getOriginAccount()).isEqualTo("1111111111");
        assertThat(found.getDestinationAccount()).isEqualTo("2222222222");
    assertThat(found.getStatus()).isEqualTo(com.fintransfer.model.TransferStatus.PENDING);
    }
}
