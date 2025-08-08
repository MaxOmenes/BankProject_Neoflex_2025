package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaymentScheduleElementDto {
    @Schema(description = "Payment number in the schedule",
            example = "1")
    private Integer number;
    @Schema(description = "Date of the payment",
            example = "2024-02-15")
    private LocalDate date;
    @Schema(description = "Total payment amount for this period (Loan + Percentages)",
            example = "3200.50")
    private BigDecimal totalPayment;
    @Schema(description = "Percentages payment amount for this operation",
            example = "1041.67")
    private BigDecimal interestPayment;
    @Schema(description = "Loan payment amount for this operation",
            example = "2158.83")
    private BigDecimal debtPayment;
    @Schema(description = "Remaining debt after this payment",
            example = "97841.17")
    private BigDecimal remainingDebt;
}
