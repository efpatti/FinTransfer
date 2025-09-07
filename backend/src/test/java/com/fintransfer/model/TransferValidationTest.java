package com.fintransfer.model;

import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TransferValidationTest {
    private final Validator validator;

    public TransferValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldRejectBlankOriginAccount() {
        Transfer transfer = Transfer.builder()
            .originAccount("")
            .destinationAccount("2222222222")
            .transferAmount(new BigDecimal("100.00"))
            .fee(new BigDecimal("5.00"))
            .transferDate(LocalDate.now().plusDays(1))
            .scheduleDate(LocalDate.now())
            .status("PENDING")
            .build();
        Set<ConstraintViolation<Transfer>> violations = validator.validate(transfer);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldRejectLongDestinationAccount() {
        Transfer transfer = Transfer.builder()
            .originAccount("1111111111")
            .destinationAccount("22222222222") // 11 chars
            .transferAmount(new BigDecimal("100.00"))
            .fee(new BigDecimal("5.00"))
            .transferDate(LocalDate.now().plusDays(1))
            .scheduleDate(LocalDate.now())
            .status("PENDING")
            .build();
        Set<ConstraintViolation<Transfer>> violations = validator.validate(transfer);
        assertThat(violations).isNotEmpty();
    }
}
