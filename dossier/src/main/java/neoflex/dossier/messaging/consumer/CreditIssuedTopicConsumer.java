package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.CreditIssuedTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "credit-issued")
public class CreditIssuedTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final CreditIssuedTopicHandlerService creditIssuedTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        creditIssuedTopicHandlerService.handle(message);
    }
}
