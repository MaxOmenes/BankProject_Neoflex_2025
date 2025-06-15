package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.dto.PaymentScheduleElementDto;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreditDtoFactoryTest {
    @Autowired
    CreditDtoFactory factory;

    @Test
    void toDto() {
        PaymentScheduleEntity paymentEntity = PaymentScheduleEntity.builder()
                .number(1)
                .date(LocalDate.of(2023, 10, 1))
                .totalPayment(new BigDecimal("10000"))
                .debtPayment(new BigDecimal("5000"))
                .interestPayment(new BigDecimal("5000"))
                .remainingDebt(new BigDecimal("95000"))
                .build();


        CreditEntity entity = CreditEntity.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .monthlyPayment(new BigDecimal("9000"))
                .rate(new BigDecimal("12.5"))
                .psk(new BigDecimal("108000"))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .paymentSchedule(List.of(paymentEntity))
                .build();

        // Act
        CreditDto result = factory.toDto(entity);

        // Assert
        assertEquals(entity.getAmount(), result.getAmount());
        assertEquals(entity.getTerm(), result.getTerm());
        assertEquals(entity.getMonthlyPayment(), result.getMonthlyPayment());
        assertEquals(entity.getRate(), result.getRate());
        assertEquals(entity.getPsk(), result.getPsk());
        assertEquals(entity.getIsInsuranceEnabled(), result.getIsInsuranceEnabled());
        assertEquals(entity.getIsSalaryClient(), result.getIsSalaryClient());
        assertEquals(1, result.getPaymentSchedule().size());
    }
}