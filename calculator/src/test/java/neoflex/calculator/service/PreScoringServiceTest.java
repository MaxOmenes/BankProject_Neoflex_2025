package neoflex.calculator.service;

import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PreScoringServiceTest {
    @Autowired
    PreScoringService preScoringService;

    @Test
    void makeOffers() {
        LoanStatementRequestEntity entity = LoanStatementRequestEntity.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .middleName("A.")
                .email("john.doe@someemail.test").build();

        var offers = preScoringService.makeOffers(entity);
        assertNotNull(offers);
        assertEquals(4, offers.size());
        assertTrue(offers.stream().anyMatch(o -> o.getRequestedAmount().equals(new BigDecimal("100000")) &&
                o.getTerm() == 12 &&
                o.getIsInsuranceEnabled() && !o.getIsSalaryClient()));

    }
}