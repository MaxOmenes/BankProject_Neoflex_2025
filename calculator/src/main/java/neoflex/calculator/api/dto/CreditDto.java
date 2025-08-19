package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreditDto {
    @Schema(description = "Amount requested by the client")
    private BigDecimal amount;
    @Schema(description = "How many months the loan will be active")
    private Integer term;
    @Schema(description = "Monthly payment amount")
    private BigDecimal monthlyPayment;
    @Schema(description = "Percentage rate for the loan")
    private BigDecimal rate;
    @Schema(description = "Total amount of the loan offer including percentages")
    private BigDecimal psk;
    @Schema(description = "Is the loan offer available for insurance")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is the client a salary client")
    private Boolean isSalaryClient;
    @Schema(description = "Payment schedule for the loan")
    private List<PaymentScheduleElementDto> paymentSchedule;
}
