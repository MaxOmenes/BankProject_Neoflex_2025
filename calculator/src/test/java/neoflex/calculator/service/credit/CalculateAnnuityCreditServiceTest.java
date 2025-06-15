package neoflex.calculator.service.credit;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;
import neoflex.calculator.store.entity.scoring.EmploymentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CalculateAnnuityCreditServiceTest {
    @Autowired
    CalculateAnnuityCreditService service;
    @Test
    void calculateCredit() {
        // Create test data for credit calculation
        CreditEntity creditEntity = CreditEntity.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(36)
                .rate(BigDecimal.valueOf(26.5))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        // Initialize service and perform calculation

        service.calculateCredit(creditEntity);

        assertEquals(BigDecimal.valueOf(146506.64), creditEntity.getPsk());
        assertEquals(BigDecimal.valueOf(4055.74), creditEntity.getMonthlyPayment());
    }
}