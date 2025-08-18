package neoflex.deal.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import neoflex.deal.store.enums.credit.CreditStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreditAdminDto {
    @Schema(description = "Unique identifier of the credit",
            example = "00000000-4000-0000-0000-000000000001")
    private UUID creditId;

    @Schema(description = "Total credit amount",
            example = "100000")
    private BigDecimal amount;

    @Schema(description = "Credit term in months",
            example = "36")
    private Integer term;

    @Schema(description = "Monthly payment amount",
            example = "3200.50")
    private BigDecimal monthlyPayment;

    @Schema(description = "Interest rate for the credit",
            example = "12.5")
    private BigDecimal rate;

    @Schema(description = "Total cost of credit (PSK - Polnaya Stoimost Kredita)",
            example = "115218")
    private BigDecimal psk;

    @Schema(description = "Payment schedule for the credit")
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Schema(description = "Whether insurance is enabled for this credit",
            example = "true")
    private Boolean insuranceEnabled;

    @Schema(description = "Whether the client is a salary client",
            example = "true")
    private Boolean salaryClient;

    @Schema(description = "Current status of the credit",
            example = "CALCULATED")
    private CreditStatus creditStatus;
}
