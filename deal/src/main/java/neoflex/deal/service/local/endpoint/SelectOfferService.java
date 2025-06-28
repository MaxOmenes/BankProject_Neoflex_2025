package neoflex.deal.service.local.endpoint;

import lombok.AllArgsConstructor;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.enums.statement.status_history.ChangeType;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SelectOfferService {
    private final StatementRepository statementRepository;
    public void selectOffer(LoanOfferDto offer) {
        Optional<StatementEntity> statement = statementRepository.findById(offer.getStatementId());

        if (statement.isEmpty()) {
            throw new IllegalArgumentException("Statement not found for ID: " + offer.getStatementId());
        }
        StatementEntity statementEntity = statement.get();

        statementEntity.getStatusHistory().add(StatementStatusHistoryDto.builder()
                        .changeType(ChangeType.MANUAL)
                        .status(ApplicationStatus.APPROVED)
                        .timestamp(LocalDate.now())
                .build()
        );

        statementEntity.setAppliedOffer(offer);

        statementEntity.setStatus(ApplicationStatus.APPROVED);

        statementRepository.save(statementEntity);


    }
}
