package neoflex.deal.service.local.endpoint.offer;

import lombok.RequiredArgsConstructor;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.fabric.StatementStatusHistoryFabric;
import neoflex.deal.service.local.template.EmailTemplateService;
import neoflex.deal.service.remote.dossier.DossierService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SelectOfferService {
    private final StatementRepository statementRepository;
    private final StatementStatusHistoryFabric statementStatusHistoryFabric;
    private final DossierService dossierService;
    private final EmailTemplateService emailTemplateService;

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

        ClientEntity client = statement.getClient();

        Map<String, Object> templateData = Map.of(
                "client", client
        );

        String emailText = emailTemplateService.processTemplate("email/finish-registration", templateData);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.FINISH_REGISTRATION)
                .text(emailText)
                .build();

        dossierService.finishRegistration(message);
    }
}
