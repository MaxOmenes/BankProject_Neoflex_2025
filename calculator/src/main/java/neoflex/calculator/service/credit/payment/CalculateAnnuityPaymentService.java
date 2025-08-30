package neoflex.calculator.service.credit.payment;

import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.PaymentScheduleElementDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CalculateAnnuityPaymentService implements PaymentService{
    @Override
    public void calculatePaymentSchedule(CreditDto credit) {
        BigDecimal amount = credit.getAmount();
        BigDecimal rate = credit.getRate();
        Integer term = credit.getTerm();

        BigDecimal remainingAmount = credit.getAmount();

        List<PaymentScheduleElementDto> payments = new ArrayList<>();

        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(
                        BigDecimal.ONE.add(monthlyRate).pow(term), 10, RoundingMode.HALF_UP
                )
        );

        BigDecimal fraction = monthlyRate.divide(denominator, 10, RoundingMode.HALF_UP);

        BigDecimal monthlyPayment = amount.multiply(fraction);
        LocalDate currentDate = LocalDate.now();

        for (int month = 1; month <= credit.getTerm(); month++) {
            BigDecimal interest = remainingAmount.multiply(monthlyRate);
            BigDecimal principal = monthlyPayment.subtract(interest);

            remainingAmount = remainingAmount.subtract(principal).max(BigDecimal.ZERO);

            PaymentScheduleElementDto payment = PaymentScheduleElementDto.builder()
                    .number(month)
                    .date(currentDate)
                    .totalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP))
                    .interestPayment(interest.setScale(2, RoundingMode.HALF_UP))
                    .debtPayment(principal.setScale(2, RoundingMode.HALF_UP))
                    .remainingDebt(remainingAmount.setScale(2, RoundingMode.HALF_UP))
                    .build();

            payments.add(payment);

            currentDate = currentDate.plusMonths(1);
            log.info("Payment {}: Total: {}, Interest: {}, Principal: {}, Remaining Debt: {}",
                    month, payment.getTotalPayment(), payment.getInterestPayment(),
                    payment.getDebtPayment(), payment.getRemainingDebt());
        }

        credit.setPaymentSchedule(payments);
        credit.setMonthlyPayment(payments.getFirst().getTotalPayment());
    }
}
