package com.fintransfer.controller;
import com.fintransfer.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;
import com.fintransfer.model.TransferStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
@Import(TestSecurityConfig.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService transferService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn201WhenValidTransfer() throws Exception {
        Transfer transfer = Transfer.builder()
                .originAccount("1234567890")
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .fee(new BigDecimal("5.00"))
                .transferDate(LocalDate.now().plusDays(1))
                .scheduleDate(LocalDate.now())
                .status(TransferStatus.PENDING)
                .build();

        Mockito.when(transferService.createTransfer(Mockito.any(Transfer.class)))
                .thenReturn(transfer);

        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originAccount").value("1234567890"));
    }

    @Test
    void shouldReturn400WhenInvalidTransfer() throws Exception {
        Transfer transfer = Transfer.builder()
                .originAccount("") // invalid: empty
                .destinationAccount("0987654321")
                .transferAmount(new BigDecimal("100.00"))
                .fee(new BigDecimal("5.00"))
                .transferDate(LocalDate.now().plusDays(1))
                .scheduleDate(LocalDate.now())
                .status(TransferStatus.PENDING)
                .build();

        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400ForMalformedJson() throws Exception {
        String malformedJson = "{\"originAccount\": \"1234567890\", \"destinationAccount\": }";
        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400ForMissingRequiredFields() throws Exception {
        String missingFieldsJson = "{\"originAccount\": \"1234567890\"}";
        mockMvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(missingFieldsJson))
                .andExpect(status().isBadRequest());
    }
}
