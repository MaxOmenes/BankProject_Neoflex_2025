package neoflex.deal.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.CreditEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class StatementAdminDto {
    @Schema(description = "Unique identifier of the loan statement",
            example = "00000000-4000-0000-0000-000000000001")
    private UUID statementId;

    @Schema(description = "Client information associated with this statement")
    private ClientAdminDto client;

    @Schema(description = "Credit information for this statement")
    private CreditAdminDto credit;

    @Schema(description = "Current status of the loan application",
            example = "APPROVED")
    private ApplicationStatus status;

    @Schema(description = "Date when the statement was created",
            example = "2024-01-15")
    private LocalDate creationDate;

    @Schema(description = "The loan offer that was applied for this statement")
    private LoanOfferDto appliedOffer;

    @Schema(description = "Date when the loan agreement was signed",
            example = "2024-01-20")
    private LocalDate signDate;

    @Schema(description = "SES (Simple Email Service) verification code",
            example = "123456")
    private String sesCode;

    @Schema(description = "History of status changes for this statement")
    private List<StatementStatusHistoryDto> statusHistory;
}
