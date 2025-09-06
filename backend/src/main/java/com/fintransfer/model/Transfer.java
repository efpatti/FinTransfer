
package com.fintransfer.model;

import javax.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDate scheduledDate;

    @Builder.Default
    private LocalDate createdDate = LocalDate.now();
}
