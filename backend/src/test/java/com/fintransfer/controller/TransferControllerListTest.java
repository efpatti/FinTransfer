package com.fintransfer.controller;

import com.fintransfer.model.Transfer;
import com.fintransfer.service.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransferController.class)
class TransferControllerListTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService transferService;

    @Test
    void shouldReturnListOfTransfers() throws Exception {
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
        List<Transfer> transfers = Arrays.asList(t1, t2);
        Mockito.when(transferService.findAllTransfers()).thenReturn(transfers);

        mockMvc.perform(get("/transfers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originAccount").value("1111111111"))
                .andExpect(jsonPath("$[1].destinationAccount").value("4444444444"));
    }

    @Test
    void shouldReturnFilteredTransfersByDate() throws Exception {
        Transfer t1 = Transfer.builder()
            .originAccount("1111111111")
            .destinationAccount("2222222222")
            .transferAmount(new BigDecimal("100.00"))
            .fee(new BigDecimal("5.00"))
            .transferDate(LocalDate.now().plusDays(1))
            .scheduleDate(LocalDate.now())
            .status("PENDING")
            .build();
        List<Transfer> transfers = Arrays.asList(t1);
        Mockito.when(transferService.findByScheduleDateBetween(LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(transfers);

        mockMvc.perform(get("/transfers?startDate=" + LocalDate.now() + "&endDate=" + LocalDate.now().plusDays(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originAccount").value("1111111111"));
    }
}
