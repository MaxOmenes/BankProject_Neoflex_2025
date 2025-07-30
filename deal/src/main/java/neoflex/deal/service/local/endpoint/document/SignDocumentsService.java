package neoflex.deal.service.local.endpoint.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.business.document.SignClientDocumentsService;
import neoflex.deal.service.local.template.EmailTemplateService;
import neoflex.deal.service.remote.dossier.DossierService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignDocumentsService {
    private final DossierService dossierService;
    private final SignClientDocumentsService signClientDocumentsService;
    private final StatementRepository statementRepository;
    private final EmailTemplateService emailTemplateService;

    public void signDocuments(String statementId) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).get(); //TODO: Handle Optional properly
        ClientEntity client = statement.getClient();

        log.info("Signing documents for statement with ID: {}", statementId);
        signClientDocumentsService.signDocuments(statement);
        log.info("Documents signed for statement with ID: {}", statementId);
        statement.setStatus(ApplicationStatus.CREDIT_ISSUED);
        statementRepository.save(statement);

        Map<String, Object> templateData = Map.of(
                "client", client
        );

        String emailText = emailTemplateService.processTemplate("email/sign-documents", templateData);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.CREDIT_ISSUED)
                .text(emailText)
                .build();

        dossierService.creditIssued(message);
    }
}
