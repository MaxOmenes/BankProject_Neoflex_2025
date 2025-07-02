package neoflex.deal.service.local.fabric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.CreditEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatementEntityFabric {
    private final StatementStatusHistoryFabric statementStatusHistoryFabric;


    public StatementEntity create(ClientEntity client) {
        StatementEntity statement = StatementEntity.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .statusHistory(List.of(
                                statementStatusHistoryFabric.create(ApplicationStatus.PREAPPROVAL)
                        )
                ).build();
        log.info("Statement created: {}", statement);

        return statement;
    }

    public void enrich(StatementEntity statement, CreditEntity credit) {
        statement.setCredit(credit);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(
                statementStatusHistoryFabric.create(ApplicationStatus.CC_APPROVED)
        );
    }
}
