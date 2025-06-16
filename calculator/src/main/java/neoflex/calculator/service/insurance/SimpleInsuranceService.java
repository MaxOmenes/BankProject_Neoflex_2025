package neoflex.calculator.service.insurance;

import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SimpleInsuranceService implements InsuranceService{
    @Value("${constants.insurancePolicyRate}")
    private BigDecimal insurancePolicyRate;

    /**
     * Calculates the insurance payments for the offer.
     * Calculating yearly insurance payments based on the remaining amount and insurance policy rate.
     * @param entity OfferEntity containing the loan details
     * @return List of insurance payments
     */
    @Override
    public List<BigDecimal> calculateInsurance(OfferEntity entity) {
        List<BigDecimal> insurancePayments = new ArrayList<>();
        BigDecimal remainingAmount = entity.getRequestedAmount();

        BigDecimal monthlyRate = entity.getRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = entity.getMonthlyPayment();
        BigDecimal insuranceForMultiplyRate = insurancePolicyRate.divide(
                BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP
        );

        for (int month = 1; month <= entity.getTerm(); month++) {
            if ((month-1) % 12 == 0 && remainingAmount.signum() > 0) {
                BigDecimal insurancePayment = remainingAmount.multiply(insuranceForMultiplyRate);
                insurancePayments.add(insurancePayment);
            }
            BigDecimal interest = remainingAmount.multiply(monthlyRate);
            BigDecimal principal = monthlyPayment.subtract(interest);

            remainingAmount = remainingAmount.subtract(principal).max(BigDecimal.ZERO);
        }
        log.info("Insurance payments for offer calculated: {}", insurancePayments);
        return insurancePayments;
    }

    @Override
    public List<BigDecimal> calculateInsurance(CreditEntity entity) {
        List<BigDecimal> insurancePayments = new ArrayList<>();
        List<PaymentScheduleEntity> payments = entity.getPaymentSchedule();

        BigDecimal insuranceForMultiplyRate = insurancePolicyRate.divide(
                BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP
        );

        for (PaymentScheduleEntity payment : payments) {
            if (payment.getNumber()-1 % 12 == 0) {
                BigDecimal insurancePayment = payment.getRemainingDebt().add(payment.getDebtPayment()).multiply(insuranceForMultiplyRate);
                insurancePayments.add(insurancePayment);
            }
        }
        log.info("Insurance payments for credit calculated: {}", insurancePayments);
        return insurancePayments;
    }
}
