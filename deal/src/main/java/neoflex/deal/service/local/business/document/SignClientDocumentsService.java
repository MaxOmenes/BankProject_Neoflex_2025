package neoflex.deal.service.local.business.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignClientDocumentsService {
    public void signDocuments(StatementEntity statement) {
        log.info("Signing documents...");
        statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
        log.info("Documents signed successfully.");
    }
}
