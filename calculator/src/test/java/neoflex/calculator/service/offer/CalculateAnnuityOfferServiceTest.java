package neoflex.calculator.service.offer;

import neoflex.calculator.store.entity.offer.OfferEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateAnnuityOfferServiceTest {
    @Autowired
    CalculateAnnuityOfferService service; //TODO: use @MockBean for unit tests

    static Method setInsuranceRateMethod;
    static Method setSalaryClientRateMethod;
    static BigDecimal EPSILON = BigDecimal.valueOf(1);

    @BeforeAll
    static void setUp() throws NoSuchMethodException {
        Object builder = OfferEntity.builder();
        Class<?> builderClass = builder.getClass();
        setInsuranceRateMethod = builderClass.getDeclaredMethod("setInsuranceRate", Double.class);
        setSalaryClientRateMethod = builderClass.getDeclaredMethod("setSalaryClientRate", Double.class);
        setInsuranceRateMethod.setAccessible(true);
        setSalaryClientRateMethod.setAccessible(true);
    }

    /**
     * Test based on example from Raiffeisen Bank:
     * <a href="https://www.raiffeisen.ru/wiki/kak-rasschitat-annuitetnyj-platezh/">
     *     How to calculate annuity payment on a loan | Raiffeisen Bank
     * </a>
     */
    @Test
    void testCalculateOffer_RaiffeisenExample() {
        CalculateAnnuityOfferService service = new CalculateAnnuityOfferService();
        OfferEntity offer = OfferEntity.builder()
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

        OfferEntity.Builder offerBuilder = OfferEntity.builder();

        setSalaryClientRateMethod.invoke(offerBuilder, Double.valueOf(1.5));
        setInsuranceRateMethod.invoke(offerBuilder, Double.valueOf(2));

        OfferEntity offer = offerBuilder
                .requestedAmount(new BigDecimal(requestedAmount))
                .term(term)
                .rate(new BigDecimal(rate))
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

    //TODO: add test wit test all variants of isInsuranceEnabled and isSalaryClient

}