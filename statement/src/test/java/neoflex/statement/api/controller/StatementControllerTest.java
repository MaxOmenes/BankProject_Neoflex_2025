package neoflex.statement.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import neoflex.statement.service.local.endpoint.GetOffersService;
import neoflex.statement.service.local.endpoint.SelectOfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatementController.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetOffersService getOffersService;

    @MockitoBean
    private SelectOfferService selectOfferService;

    @Test
    void getOffers_shouldReturnListOfOffers_whenValidRequest() throws Exception {
        // Arrange
        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .middleName("Smith")
                .email("john.doe@example.com")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();

        List<LoanOfferDto> expectedOffers = List.of(
                LoanOfferDto.builder()
                        .statementId(UUID.randomUUID())
                        .requestedAmount(BigDecimal.valueOf(100000))
                        .totalAmount(BigDecimal.valueOf(110000))
                        .term(12)
                        .monthlyPayment(BigDecimal.valueOf(9167))
                        .rate(BigDecimal.valueOf(10.5))
                        .isInsuranceEnabled(true)
                        .isSalaryClient(false)
                        .build()
        );

        when(getOffersService.getOffers(any(LoanStatementRequestDto.class))).thenReturn(expectedOffers);

        // Act & Assert
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].statementId").exists())
                .andExpect(jsonPath("$[0].requestedAmount").value(100000))
                .andExpect(jsonPath("$[0].totalAmount").value(110000))
                .andExpect(jsonPath("$[0].term").value(12))
                .andExpect(jsonPath("$[0].monthlyPayment").value(9167))
                .andExpect(jsonPath("$[0].rate").value(10.5))
                .andExpect(jsonPath("$[0].isInsuranceEnabled").value(true))
                .andExpect(jsonPath("$[0].isSalaryClient").value(false));

        verify(getOffersService, times(1)).getOffers(any(LoanStatementRequestDto.class));
    }

    @Test
    void getOffers_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Arrange - Invalid request with missing required fields
        LoanStatementRequestDto invalidRequest = LoanStatementRequestDto.builder()
                .amount(null) // Missing required field
                .build();

        // Act & Assert
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(getOffersService, never()).getOffers(any(LoanStatementRequestDto.class));
    }

    @Test
    void getOffers_shouldReturnBadRequest_whenEmptyRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(getOffersService, never()).getOffers(any(LoanStatementRequestDto.class));
    }

    @Test
    void selectOffer_shouldReturnOk_whenValidOffer() throws Exception {
        // Arrange
        LoanOfferDto offer = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(110000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9167))
                .rate(BigDecimal.valueOf(10.5))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        doNothing().when(selectOfferService).selectOffer(any(LoanOfferDto.class));

        // Act & Assert
        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offer)))
                .andExpect(status().isOk());

        verify(selectOfferService, times(1)).selectOffer(any(LoanOfferDto.class));
    }

    @Test
    void selectOffer_shouldReturnOk_whenEmptyOffer() throws Exception {
        // Arrange
        LoanOfferDto emptyOffer = LoanOfferDto.builder().build();

        doNothing().when(selectOfferService).selectOffer(any(LoanOfferDto.class));

        // Act & Assert
        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyOffer)))
                .andExpect(status().isOk());

        verify(selectOfferService, times(1)).selectOffer(any(LoanOfferDto.class));
    }
}