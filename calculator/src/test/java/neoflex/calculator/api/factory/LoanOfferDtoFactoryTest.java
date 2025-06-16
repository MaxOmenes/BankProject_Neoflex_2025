package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class LoanOfferDtoFactoryTest {

    @Autowired
    LoanOfferDtoFactory factory;
    @Test
    void toDto() {
        // Given
        OfferEntity entity = OfferEntity.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(1000))
                .totalAmount(BigDecimal.valueOf(1200))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(100))
                .rate(BigDecimal.valueOf(10))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        // When
        LoanOfferDto dto = factory.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getStatementId(), dto.getStatementId());
        assertEquals(entity.getRequestedAmount(), dto.getRequestedAmount());
        assertEquals(entity.getTotalAmount(), dto.getTotalAmount());
        assertEquals(entity.getTerm(), dto.getTerm());
        assertEquals(entity.getMonthlyPayment(), dto.getMonthlyPayment());
        assertEquals(entity.getRate(), dto.getRate());
        assertEquals(entity.getIsInsuranceEnabled(), dto.getIsInsuranceEnabled());
        assertEquals(entity.getIsSalaryClient(), dto.getIsSalaryClient());
    }
}