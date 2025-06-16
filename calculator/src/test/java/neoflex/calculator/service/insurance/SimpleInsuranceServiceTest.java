package neoflex.calculator.service.insurance;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        setInsuranceRateMethod.invoke(offerBuilder, 0.5);

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
        assertEquals(new BigDecimal(500).setScale(2, RoundingMode.HALF_UP),
                insurancePayments.getFirst().setScale(2, RoundingMode.HALF_UP));
    }


    @Test
    void testCalculateInsurance() {

        List<PaymentScheduleEntity> payments = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            payments.add(PaymentScheduleEntity.builder()
                    .number(i + 1)
                    .totalPayment(new BigDecimal("8884.88"))
                    .interestPayment(new BigDecimal("1000"))
                    .debtPayment(new BigDecimal("7884.88"))
                    .remainingDebt(new BigDecimal("100000").subtract(new BigDecimal("7884.88").multiply(BigDecimal.valueOf(i + 1))))
                    .build());
        }

        CreditEntity entity = CreditEntity.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .rate(new BigDecimal("12"))
                .paymentSchedule(payments)
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        var insurancePayments = service.calculateInsurance(entity);

        assertNotNull(insurancePayments);
        assertEquals(1, insurancePayments.size());
        assertEquals(new BigDecimal("500.00").setScale(2, RoundingMode.HALF_UP),
                insurancePayments.getFirst().setScale(2, RoundingMode.HALF_UP));
    }
}