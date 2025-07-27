package neoflex.deal.service.local.endpoint.offer;

import lombok.RequiredArgsConstructor;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.service.local.fabric.StatementStatusHistoryFabric;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelectOfferService {
    private final StatementRepository statementRepository;
    private final StatementStatusHistoryFabric statementStatusHistoryFabric;

    public void selectOffer(LoanOfferDto offer) {
        StatementEntity statement = statementRepository.findById(offer.getStatementId()).orElseThrow(
                () -> new IllegalArgumentException("Statement not found for ID: " + offer.getStatementId())
        );

        statement.getStatusHistory()
                .add(statementStatusHistoryFabric.create(ApplicationStatus.APPROVED)
                );

        statement.setAppliedOffer(offer);
        statement.setStatus(ApplicationStatus.APPROVED);

        statementRepository.save(statement);
    }
}
