package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.FinishRegistrationTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "finish-registration")
public class FinishRegistrationTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final FinishRegistrationTopicHandlerService finishRegistrationTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        finishRegistrationTopicHandlerService.handle(message);
    }
}
