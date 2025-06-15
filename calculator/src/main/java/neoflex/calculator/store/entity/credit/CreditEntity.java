package neoflex.calculator.store.entity.credit;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CreditEntity {
    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<PaymentScheduleEntity> paymentSchedule;
}
