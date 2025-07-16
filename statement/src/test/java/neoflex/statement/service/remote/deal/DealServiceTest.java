package neoflex.statement.service.remote.deal;

import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@EnableWireMock
class DealServiceTest {
    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    DealService dealService;

    @Test
    void getOffers_goodData() {
//        String responseBody = """
//                [
//                   {
//                     "statementId": "00000000-0000-0000-0000-000000000001",
//                     "requestedAmount": 20000,
//                     "totalAmount": 20000,
//                     "term": 36,
//                     "monthlyPayment": 5000,
//                     "rate": 1,
//                     "isInsuranceEnabled": true,
//                     "isSalaryClient": true
//                   }
//                 ]
//                """;
//        stubFor(post("/statement").willReturn(ok(responseBody)));
//
//        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
//                .email("venom.snake@diamonddogs.com")
//                .amount(BigDecimal.valueOf(100))
//                .term(36)
//                .firstName("Snake")
//                .lastName("Solid")
//                .middleName("John")
//                .birthdate(LocalDate.parse("1972-01-01"))
//                .passportSeries("1234")
//                .passportNumber("567890")
//                .build();
//        List<LoanOfferDto> offers = dealService.getOffers(request);
//
//        assertEquals(1, offers.size());
//        assertEquals("00000000-0000-0000-0000-000000000001", offers.getFirst().getStatementId().toString());
//        assertEquals("20000", offers.getFirst().getRequestedAmount().toString());
//        assertEquals("20000", offers.getFirst().getTotalAmount().toString());
//        assertEquals(36, offers.getFirst().getTerm());
//        assertEquals("5000", offers.getFirst().getMonthlyPayment().toString());
//        assertEquals(BigDecimal.ONE, offers.getFirst().getRate());
//        assertTrue(offers.getFirst().getIsInsuranceEnabled());
//        assertTrue(offers.getFirst().getIsSalaryClient());
    }

    @Test
    void selectOffer() {
    }
}