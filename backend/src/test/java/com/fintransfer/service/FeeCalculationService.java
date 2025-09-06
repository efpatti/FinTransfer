package com.fintransfer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class FeeCalculatorServiceTest {

    private FeeCalculatorService feeCalculator;

    @BeforeEach
    void setUp() {
        feeCalculator = new FeeCalculatorService();
    }

    @Test
    void shouldCalculateFeeFor0to3Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(2));
        assertThat(fee).isEqualByComparingTo("25.00");
    }

    @Test
    void shouldCalculateFeeFor4to10Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(5));
        assertThat(fee).isEqualByComparingTo("12.00");
    }

    @Test
    void shouldCalculateFeeFor11to20Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(15));
        assertThat(fee).isEqualByComparingTo("82.00");
    }

    @Test
    void shouldCalculateFeeFor21to30Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(25));
        assertThat(fee).isEqualByComparingTo("69.00");
    }

    @Test
    void shouldCalculateFeeFor31to40Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(35));
        assertThat(fee).isEqualByComparingTo("47.00");
    }

    @Test
    void shouldCalculateFeeFor41to50Days() {
        BigDecimal fee = feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(45));
        assertThat(fee).isEqualByComparingTo("17.00");
    }

    @Test
    void shouldThrowExceptionForInvalidRange() {
        assertThatThrownBy(() -> feeCalculator.calculateFee(new BigDecimal("1000.00"),
                LocalDate.now(), LocalDate.now().plusDays(60)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No applicable fee");
    }
}
