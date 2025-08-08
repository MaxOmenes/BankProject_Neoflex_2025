package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoanOfferDto {
    @Schema(description = "Unique identifier of the statement",
    example = "00000000-4000-0000-0000-000000000000")
    private UUID statementId;
    @Schema(description = " Amount requested by the client",
            example = "100000")
    private BigDecimal requestedAmount;
    @Schema(description = "Total amount of the loan offer including percentages and fees",
            example = "120000")
    private BigDecimal totalAmount;
    @Schema(description = "How many months the loan will be active",
            example = "36")
    private Integer term;
    @Schema(description = "Monthly payment amount",
            example = "4000")
    private BigDecimal monthlyPayment;
    @Schema(description = "Percentage rate for the loan",
            example = "0.15")
    private BigDecimal rate;
    @Schema(description = "Is the loan offer available for insurance",
            example = "true")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Is the client a salary client",
            example = "false")
    private Boolean isSalaryClient;
}
