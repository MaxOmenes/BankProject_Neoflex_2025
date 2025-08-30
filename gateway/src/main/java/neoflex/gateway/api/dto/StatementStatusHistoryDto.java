package neoflex.gateway.api.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import neoflex.gateway.store.enums.statement.ApplicationStatus;
import neoflex.gateway.store.enums.statement.status_history.ChangeType;

import java.time.LocalDate;

@Data
@Builder
public class StatementStatusHistoryDto {
    private ApplicationStatus status;
    private LocalDate timestamp; //TODO: in spec it`s time, maybe change

    @Nullable
    private ChangeType changeType;
}
