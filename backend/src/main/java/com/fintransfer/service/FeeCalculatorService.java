package com.fintransfer.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class FeeCalculatorService {

    public BigDecimal calculateFee(BigDecimal amount, LocalDate scheduledDate, LocalDate transferDate) {
        long daysBetween = ChronoUnit.DAYS.between(scheduledDate, transferDate);

        if (daysBetween >= 0 && daysBetween <= 3) {
            // 2.5% of amount
            return amount.multiply(new BigDecimal("0.025")).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 4 && daysBetween <= 10) {
            // Fixed fee 12.00
            return new BigDecimal("12.00");
        } else if (daysBetween >= 11 && daysBetween <= 20) {
            return amount.multiply(new BigDecimal("0.082")).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 21 && daysBetween <= 30) {
            return amount.multiply(new BigDecimal("0.069")).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 31 && daysBetween <= 40) {
            return amount.multiply(new BigDecimal("0.047")).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 41 && daysBetween <= 50) {
            return amount.multiply(new BigDecimal("0.017")).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new IllegalArgumentException("No applicable fee for transfer date: " + transferDate);
        }
    }
}
