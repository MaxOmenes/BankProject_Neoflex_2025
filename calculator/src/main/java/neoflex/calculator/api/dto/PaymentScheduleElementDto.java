package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaymentScheduleElementDto {
    @Schema(description = "Payment number in the schedule")
    private Integer number;
    @Schema(description = "Date of the payment")
    private LocalDate date;
    @Schema(description = "Total payment amount for this period (Loan + Percentages)")
    private BigDecimal totalPayment;
    @Schema(description = "Percentages payment amount for this operation")
    private BigDecimal interestPayment;
    @Schema(description = "Loan payment amount for this operation")
    private BigDecimal debtPayment;
    @Schema(description = "Remaining debt after this payment")
    private BigDecimal remainingDebt;
}
