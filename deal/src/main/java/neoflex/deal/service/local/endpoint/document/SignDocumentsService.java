package neoflex.deal.service.local.endpoint.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.business.document.SignClientDocumentsService;
import neoflex.deal.service.remote.dossier.DossierService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignDocumentsService {
    private final DossierService dossierService;
    private final SignClientDocumentsService signClientDocumentsService;
    private final StatementRepository statementRepository;

    public void signDocuments(String statementId) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).get(); //TODO: Handle Optional properly
        ClientEntity client = statement.getClient();

        log.info("Signing documents for statement with ID: {}", statementId);
        signClientDocumentsService.signDocuments(statement);
        log.info("Documents signed for statement with ID: {}", statementId);
        statement.setStatus(ApplicationStatus.CREDIT_ISSUED);
        statementRepository.save(statement);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.CREDIT_ISSUED)
                .text("""
                        Dear %s %s %s,
                        Your credit application has been successfully processed and the documents have been signed.
                        Thank you for choosing our service.
                        
                        Best regards,
                        Your Bank with love
                        """)
                .build();

        dossierService.creditIssued(message);
    }
}
