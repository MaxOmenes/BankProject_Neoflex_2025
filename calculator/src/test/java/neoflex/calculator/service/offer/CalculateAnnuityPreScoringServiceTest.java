package neoflex.calculator.service.offer;

import neoflex.calculator.api.dto.LoanOfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CalculateAnnuityPreScoringServiceTest {
    @Autowired
    CalculateAnnuityOfferService service;

    static Method setInsuranceRateMethod;
    static Method setSalaryClientRateMethod;
    static BigDecimal EPSILON = BigDecimal.valueOf(1);

    /**
     * Test based on example from Raiffeisen Bank:
     * <a href="https://www.raiffeisen.ru/wiki/kak-rasschitat-annuitetnyj-platezh/">
     *     How to calculate annuity payment on a loan | Raiffeisen Bank
     * </a>
     */
    @Test
    void testCalculateOffer_RaiffeisenExample() {

        LoanOfferDto offer = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("20000"))
                .term(36)
                .rate(new BigDecimal("12"))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();

        service.calculateOffer(offer);

        assertEquals(BigDecimal.valueOf(664.29), offer.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP));
    }
    @ParameterizedTest
    @CsvSource(value = {"100000,36,12,3321.43,119571.48,false,false",
                        "100000,36,12,3226.72,117195.01,true,false",
                        "100000,36,12,3250.24,117008.64,false,true",
                        "100000,36,12,3156.75,114671.33,true,true"}, delimiter = ',')
    void testCalculateOffer(int requestedAmount, int term, Double rate, Double expectedMonthlyPayment,
                           Double expectedTotalAmount, boolean isInsuranceEnabled, boolean isSalaryClient) throws InvocationTargetException, IllegalAccessException {

        BigDecimal currentRate = BigDecimal.valueOf(rate); //TODO: (!!!) This is not checking of prescoring, just a simple calculation (!!!)

        if (isInsuranceEnabled) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(2));
        }

        if (isSalaryClient) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(1.5));
        }

        LoanOfferDto offer = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal(requestedAmount))
                .term(term)
                .rate(currentRate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        service.calculateOffer(offer);

        assertEquals(new BigDecimal(expectedMonthlyPayment).setScale(2, RoundingMode.HALF_UP),
                offer.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP));
        assertTrue(new BigDecimal(expectedTotalAmount).setScale(2, RoundingMode.HALF_UP).subtract(
                        offer.getTotalAmount().setScale(2, RoundingMode.HALF_UP))
                .abs()
                .compareTo(EPSILON) < 0,
                String.format("Actual total amount: %s, expected: %s", expectedTotalAmount,offer.getTotalAmount()));
    }

}