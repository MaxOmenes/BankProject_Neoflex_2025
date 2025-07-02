package neoflex.deal.service.local.fabric;

import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.enums.statement.status_history.ChangeType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StatementStatusHistoryFabric {
    public StatementStatusHistoryDto create(ApplicationStatus status) { //TODO: change when use custom logic of creation
        return StatementStatusHistoryDto.builder()
                .changeType(ChangeType.MANUAL) //TODO: (!) when it change?
                .timestamp(LocalDate.now())
                .status(status)
                .build();
    }
}
