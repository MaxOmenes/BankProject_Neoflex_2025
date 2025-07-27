package neoflex.deal.service.local.endpoint.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.dto.enums.Subject;
import neoflex.deal.service.local.business.ses.SesCodeService;
import neoflex.deal.service.remote.dossier.DossierService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendSesCodeService {
    private final DossierService dossierService;
    private final SesCodeService sesCodeService;
    private final StatementRepository statementRepository;

    public void sendSes(String statementId) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).get(); //TODO: Handle Optional properly
        ClientEntity client = statement.getClient();

        sesCodeService.createCode(statement);
        statementRepository.save(statement);

        EmailMessageDto message = EmailMessageDto.builder()
                .address(client.getEmail())
                .statementId(statement.getStatementId())
                .subject(Subject.SEND_SES) //TODO: CHANGE EMAIL TEXT
                .text(""" 
                        Dear %s %s %s,
                        Your documents have been prepared for signing.
                        Please review and sign them in your personal account.
                        
                        Your sign code:
                        %s
                        
                        Best regards,
                        Your Bank with love
                        """.formatted(client.getFirstName(),
                        client.getMiddleName(),
                        client.getLastName(),
                        statement.getSesCode())
                )
                .build();

        log.info("Signing documents for client: {}", client.getEmail());
        dossierService.sendSes(message);
        log.info("Documents signed for client: {}", client.getEmail());
    }
}
