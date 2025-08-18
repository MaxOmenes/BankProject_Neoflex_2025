package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import neoflex.gateway.store.enums.statement.ApplicationStatus;
import neoflex.gateway.store.enums.statement.status_history.ChangeType;

import java.time.LocalDate;

@Data
@Builder
public class StatementStatusHistoryDto {
    @Schema(description = "Current status of the application",
            example = "APPROVED")
    private ApplicationStatus status;

    @Schema(description = "Date when the status was changed",
            example = "2024-01-15")
    private LocalDate timestamp; //TODO: in spec it`s time, maybe change

    @Nullable
    @Schema(description = "Type of change that occurred",
            example = "AUTOMATIC")
    private ChangeType changeType;
}
