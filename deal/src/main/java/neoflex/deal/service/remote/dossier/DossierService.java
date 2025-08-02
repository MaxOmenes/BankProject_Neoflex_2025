package neoflex.deal.service.remote.dossier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.messaging.dto.EmailMessageDto;
import neoflex.deal.messaging.producer.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DossierService {

    private final FinishRegistrationTopicProducer finishRegistrationTopicProducer;
    private final CreateDocumentsTopicProducer createDocumentsTopicProducer;
    private final SendDocumentsTopicProducer sendDocumentsTopicProducer;
    private final SendSesTopicProducer sendSesTopicProducer;
    private final CreditIssuedTopicProducer creditIssuedTopicProducer;
    private final StatementDeniedTopicProducer statementDeniedTopicProducer;

    public void finishRegistration(EmailMessageDto message){
        log.info("Finishing registration for message: {}", message);
        finishRegistrationTopicProducer.send(message);
    }

    public void createDocuments(EmailMessageDto message){
        log.info("Creating documents for message: {}", message);
        createDocumentsTopicProducer.send(message);
    }

    public void sendDocuments(EmailMessageDto message){
        log.info("Sending documents for message: {}", message);
        sendDocumentsTopicProducer.send(message);
    }

    public void sendSes(EmailMessageDto message){
        log.info("Sending SES for message: {}", message);
        sendSesTopicProducer.send(message);
    }

    public void creditIssued(EmailMessageDto message){
        log.info("Credit issued for message: {}", message);
        creditIssuedTopicProducer.send(message);
    }

    public void statementDenied(EmailMessageDto message){
        log.info("Statement denied for message: {}", message);
        statementDeniedTopicProducer.send(message);
    }
}
