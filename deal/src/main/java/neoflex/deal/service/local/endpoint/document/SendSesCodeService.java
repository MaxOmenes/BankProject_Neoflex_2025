package neoflex.deal.service.local.endpoint.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.business.ses.SesCodeService;
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
public class SendSesCodeService {
    private final DossierService dossierService;
    private final SesCodeService sesCodeService;
    private final StatementRepository statementRepository;
    private final EmailTemplateService emailTemplateService;

    public void sendSes(String statementId) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).get(); //TODO: Handle Optional properly
        ClientEntity client = statement.getClient();

        sesCodeService.createCode(statement);
        statementRepository.save(statement);

        Map<String, Object> templateData = Map.of(
          "client", client,
          "statement", statement
        );

        String emailText = emailTemplateService.processTemplate("email/send-ses", templateData);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.SEND_SES) //TODO: CHANGE EMAIL TEXT
                .text(emailText)
                .build();

        log.info("Signing documents for client: {}", client.getEmail());
        dossierService.sendSes(message);
        log.info("Documents signed for client: {}", client.getEmail());
    }
}
