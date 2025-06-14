package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoanOfferDto {
    @Schema(description = "Unique identifier of the statement")
    UUID statementId;
    @Schema(description = " Amount requested by the client")
    BigDecimal requestedAmount;
    @Schema(description = "Total amount of the loan offer including percentages and fees")
    BigDecimal totalAmount;
    @Schema(description = "How many months the loan will be active")
    Integer term;
    @Schema(description = "Monthly payment amount")
    BigDecimal monthlyPayment;
    @Schema(description = "Percentage rate for the loan")
    BigDecimal rate;
    @Schema(description = "Is the loan offer available for insurance")
    Boolean isInsuranceEnabled;
    @Schema(description = "Is the client a salary client")
    Boolean isSalaryClient;
}
