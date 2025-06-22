package neoflex.calculator.service.credit.payment;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class CalculateAnnuityPaymentServiceTest {
    @Autowired
    private CalculateAnnuityPaymentService calculateAnnuityPaymentService;
    @Test
    void calculatePaymentSchedule() {
        CreditEntity creditEntity = CreditEntity.builder()
                .amount(BigDecimal.valueOf(100000))
                .rate(BigDecimal.valueOf(12))
                .term(12)
                .build();

        calculateAnnuityPaymentService.calculatePaymentSchedule(creditEntity);

        // Assert
        assertNotNull(creditEntity.getPaymentSchedule());
        assertEquals(12, creditEntity.getPaymentSchedule().size());

        PaymentScheduleEntity firstPayment = creditEntity.getPaymentSchedule().getFirst();
        assertEquals(new BigDecimal("8884.88"), firstPayment.getTotalPayment());
        assertEquals(new BigDecimal("1000.00"), firstPayment.getInterestPayment());
        assertEquals(new BigDecimal("7884.88"), firstPayment.getDebtPayment());
    }
}