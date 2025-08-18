package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreditDto {
    @Schema(description = "Amount requested by the client",
            example = "100000")
    private BigDecimal amount;
    @Schema(description = "How many months the loan will be active",
            example = "36")
    private Integer term;
    @Schema(description = "Monthly payment amount",
            example = "3200.50")
    private BigDecimal monthlyPayment;
    @Schema(description = "Percentage rate for the loan",
            example = "12.5")
    private BigDecimal rate;
    @Schema(description = "Total amount of the loan offer including percentages",
            example = "115218")
    private BigDecimal psk;
    @Schema(description = "Is the loan offer available for insurance",
            example = "true")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is the client a salary client",
            example = "true")
    private Boolean isSalaryClient;
    @Schema(description = "Payment schedule for the loan")
    private List<PaymentScheduleElementDto> paymentSchedule;
}
