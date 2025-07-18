package neoflex.statement.service.remote.deal;

import com.fasterxml.jackson.databind.ObjectMapper;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = DealServiceTest_2.AppConfiguration.class)
@EnableWireMock
class DealServiceTest_2 {

    @Autowired
    private DealService dealService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @BeforeEach
    void setUp() {
        // Configure service to use WireMock URLs
        ReflectionTestUtils.setField(dealService, "statementEndpoint", wireMockUrl + "/statement");
        ReflectionTestUtils.setField(dealService, "selectOfferEndpoint", wireMockUrl + "/offer");

        // Reset WireMock stubs
        reset();
    }

    @Test
    void getOffers_shouldReturnOffers_whenValidRequest() throws Exception {
        // Arrange
        List<LoanOfferDto> expectedOffers = List.of(
                LoanOfferDto.builder()
                        .statementId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                        .requestedAmount(BigDecimal.valueOf(20000))
                        .totalAmount(BigDecimal.valueOf(20000))
                        .term(36)
                        .monthlyPayment(BigDecimal.valueOf(5000))
                        .rate(BigDecimal.ONE)
                        .isInsuranceEnabled(true)
                        .isSalaryClient(true)
                        .build()
        );

        stubFor(post(urlEqualTo("/statement"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedOffers))));

        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .email("venom.snake@diamonddogs.com")
                .amount(BigDecimal.valueOf(100))
                .term(36)
                .firstName("Snake")
                .lastName("Solid")
                .middleName("John")
                .birthdate(LocalDate.parse("1972-01-01"))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();

        // Act
        List<LoanOfferDto> offers = dealService.getOffers(request);

        // Assert
        assertEquals(1, offers.size());
        assertEquals("00000000-0000-0000-0000-000000000001", offers.get(0).getStatementId().toString());
        assertEquals(BigDecimal.valueOf(20000), offers.get(0).getRequestedAmount());
        assertEquals(BigDecimal.valueOf(20000), offers.get(0).getTotalAmount());
        assertEquals(36, offers.get(0).getTerm());
        assertEquals(BigDecimal.valueOf(5000), offers.get(0).getMonthlyPayment());
        assertEquals(BigDecimal.ONE, offers.get(0).getRate());
        assertTrue(offers.get(0).getIsInsuranceEnabled());
        assertTrue(offers.get(0).getIsSalaryClient());

        // Verify the request was made
        verify(postRequestedFor(urlEqualTo("/statement"))
                .withRequestBody(containing("venom.snake@diamonddogs.com")));
    }

    @Test
    void selectOffer_shouldCompleteSuccessfully_whenValidOffer() throws Exception {
        // Arrange
        LoanOfferDto offer = LoanOfferDto.builder()
                .statementId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .requestedAmount(BigDecimal.valueOf(20000))
                .totalAmount(BigDecimal.valueOf(20000))
                .term(36)
                .monthlyPayment(BigDecimal.valueOf(5000))
                .rate(BigDecimal.ONE)
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        stubFor(post(urlEqualTo("/offer"))
                .willReturn(aResponse()
                        .withStatus(200)));

        // Act
        assertDoesNotThrow(() -> dealService.selectOffer(offer));

        // Assert
        verify(postRequestedFor(urlEqualTo("/offer"))
                .withRequestBody(containing("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    void getOffers_shouldThrowException_whenServerError() {
        // Arrange
        stubFor(post(urlEqualTo("/statement"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Internal Server Error")));

        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .email("test@example.com")
                .amount(BigDecimal.valueOf(100))
                .term(36)
                .firstName("Test")
                .lastName("User")
                .birthdate(LocalDate.parse("1990-01-01"))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();

        // Act & Assert
        assertThrows(Exception.class, () -> dealService.getOffers(request));
    }

    @SpringBootApplication
    static class AppConfiguration {
        @Bean
        public DealService dealService(RestClient.Builder restClientBuilder) {
            return new DealService(restClientBuilder.build());
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public RestClient.Builder restClientBuilder() {
            return RestClient.builder();
        }
    }
}