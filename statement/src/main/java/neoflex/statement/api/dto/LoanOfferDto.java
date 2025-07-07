package neoflex.statement.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoanOfferDto {
    @Schema(description = "Unique identifier of the statement")
    private UUID statementId;
    @Schema(description = " Amount requested by the client")
    private BigDecimal requestedAmount;
    @Schema(description = "Total amount of the loan offer including percentages and fees")
    private BigDecimal totalAmount;
    @Schema(description = "How many months the loan will be active")
    private Integer term;
    @Schema(description = "Monthly payment amount")
    private BigDecimal monthlyPayment;
    @Schema(description = "Percentage rate for the loan")
    private BigDecimal rate;
    @Schema(description = "Is the loan offer available for insurance")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is the client a salary client")
    private Boolean isSalaryClient;
}
