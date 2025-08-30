package neoflex.calculator.service.offer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.service.insurance.InsuranceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CalculateAnnuityOfferService implements OfferService {

    private final InsuranceService insuranceService;

    /**
     * Calculates the offer based on the requested amount, rate, and term.
     * <p> Method will add the following fields to the entity:
     * <ul>
     *  <li> monthlyPayment: the monthly payment amount</li>
     *  <li> totalAmount: the total payment amount over the term</li>
     * </ul>
     * <p><a href="https://www.raiffeisen.ru/wiki/kak-rasschitat-annuitetnyj-platezh/">
     *     How to calculate annuity payment on a loan | Raiffeisen Bank
     * </a>
     *
     * @param offer OfferEntity containing the loan details
     */
    @Override
    public void calculateOffer(LoanOfferDto offer) {
        BigDecimal requestedAmount = offer.getRequestedAmount();
        BigDecimal rate = offer.getRate();
        Integer term = offer.getTerm();

        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(
                        BigDecimal.ONE.add(monthlyRate).pow(term), 10, RoundingMode.HALF_UP
                )
        );

        BigDecimal fraction = monthlyRate.divide(denominator, 10, RoundingMode.HALF_UP);

        BigDecimal monthlyPayment = requestedAmount.multiply(fraction);

        offer.setMonthlyPayment(monthlyPayment);
        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        if (offer.getIsInsuranceEnabled()) {
            List<BigDecimal> insurancePayments = insuranceService.calculateInsurance(offer);
            BigDecimal totalInsurance = insurancePayments.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalAmount = totalAmount.add(totalInsurance);
        }

        offer.setTotalAmount(totalAmount);
        log.info("Calculated offer: {}", offer);
    }
}
