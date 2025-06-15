package neoflex.calculator.service.credit.payment;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculateAnnuityPaymentService implements PaymentService{
    @Override
    public void calculatePaymentSchedule(CreditEntity entity) {
        BigDecimal amount = entity.getAmount();
        BigDecimal rate = entity.getRate();
        Integer term = entity.getTerm();

        BigDecimal remainingAmount = entity.getAmount();

        List<PaymentScheduleEntity> payments = new ArrayList<>();

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

        for (int month = 1; month <= entity.getTerm(); month++) {
            BigDecimal interest = remainingAmount.multiply(monthlyRate);
            BigDecimal principal = monthlyPayment.subtract(interest);

            remainingAmount = remainingAmount.subtract(principal).max(BigDecimal.ZERO);

            PaymentScheduleEntity payment = PaymentScheduleEntity.builder()
                    .number(month)
                    .date(currentDate)
                    .totalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP))
                    .interestPayment(interest.setScale(2, RoundingMode.HALF_UP))
                    .debtPayment(principal.setScale(2, RoundingMode.HALF_UP))
                    .remainingDebt(remainingAmount.setScale(2, RoundingMode.HALF_UP))
                    .build();

            payments.add(payment);

            currentDate = currentDate.plusMonths(1);

        }

        entity.setPaymentSchedule(payments);
        entity.setMonthlyPayment(payments.getFirst().getTotalPayment());
    }
}
