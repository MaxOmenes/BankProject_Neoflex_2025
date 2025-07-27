package neoflex.deal.service.local.business.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrepareDocumentsService {

    public void prepareDocuments(StatementEntity statement) {
        log.info("Preparing documents for the deal...");
        statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        // TODO: Maybe call me baby
        statement.setStatus(ApplicationStatus.DOCUMENTS_CREATED);
        log.info("Documents prepared successfully.");
    }
}
