package neoflex.deal.service.local.endpoint.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.business.document.PrepareDocumentsService;
import neoflex.deal.service.local.template.EmailTemplateService;
import neoflex.deal.service.remote.dossier.DossierService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendDocumentsService {
    private final DossierService dossierService;
    private final PrepareDocumentsService prepareDocumentsService;
    private final StatementRepository statementRepository;
    private final EmailTemplateService emailTemplateService;

    public void sendDocuments(String statementId) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).get(); //TODO: Handle Optional properly
        ClientEntity client = statement.getClient();

        prepareDocumentsService.prepareDocuments(statement);
        statementRepository.save(statement);

        Map<String, Object> templateData = Map.of(
                "client", client
        );

        String emailText = emailTemplateService.processTemplate("email/send-documents", templateData);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.SEND_DOCUMENTS) //TODO:CHANGE EMAIL TEXT
                .text(emailText)
                .build();

        log.info("Sending documents for dossier ID: {}", statementId);
        dossierService.sendDocuments(message);
        log.info("Documents sent successfully for dossier ID: {}", statementId);
    }
}
