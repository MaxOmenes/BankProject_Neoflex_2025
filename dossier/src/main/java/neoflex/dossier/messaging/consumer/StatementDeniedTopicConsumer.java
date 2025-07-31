package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.StatementDeniedTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics="statement-denied")
public class StatementDeniedTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final StatementDeniedTopicHandlerService statementDeniedTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        statementDeniedTopicHandlerService.handle(message);
    }
}
