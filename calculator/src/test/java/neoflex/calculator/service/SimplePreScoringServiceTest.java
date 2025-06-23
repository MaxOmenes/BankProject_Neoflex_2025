package neoflex.calculator.service;

import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.service.prescoring.SimplePreScoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SimplePreScoringServiceTest {
    @Autowired
    SimplePreScoringService simplePreScoringService;

    @Test
    void preScore() {
        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .middleName("A.")
                .email("john.doe@someemail.test")
                .build();

        var offers = simplePreScoringService.preScore(request);
        assertNotNull(offers);
        assertEquals(4, offers.size());
        assertTrue(offers.stream().anyMatch(o -> o.getRequestedAmount().equals(new BigDecimal("100000")) &&
                o.getTerm() == 12 &&
                o.getIsInsuranceEnabled() && !o.getIsSalaryClient()));

    }
}