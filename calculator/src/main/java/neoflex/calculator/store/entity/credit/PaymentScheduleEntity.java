package neoflex.calculator.store.entity.credit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
public class PaymentScheduleEntity {
    Integer number;
    LocalDate date;
    BigDecimal totalPayment;
    BigDecimal interestPayment;
    BigDecimal debtPayment;
    BigDecimal remainingDebt;
}
