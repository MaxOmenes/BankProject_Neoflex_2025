package neoflex.calculator.service.insurance;

import neoflex.calculator.store.entity.offer.OfferEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SimpleInsuranceServiceTest {
    @Autowired
    InsuranceService service; //TODO: use @MockBean for unit tests

    @Test
    void calculateInsurance() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException { //TODO: add test with any values
        Object builder = OfferEntity.builder();
        Class<?> builderClass = builder.getClass();
        Method setInsuranceRateMethod = builderClass.getDeclaredMethod("setInsuranceRate", Double.class);
        setInsuranceRateMethod.setAccessible(true);

        OfferEntity.Builder offerBuilder = OfferEntity.builder();

        setInsuranceRateMethod.invoke(offerBuilder, Double.valueOf(0.5));

        OfferEntity offerEntity = offerBuilder
                .requestedAmount(new BigDecimal("100000"))
                .term(12)
                .monthlyPayment(new BigDecimal(8884.88))
                .rate(new BigDecimal("12"))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        var insurancePayments = service.calculateInsurance(offerEntity);


        assertNotNull(insurancePayments);
        assertEquals(1, insurancePayments.size());
        assertEquals(new BigDecimal(500).setScale(2, BigDecimal.ROUND_HALF_UP),
                insurancePayments.getFirst().setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}