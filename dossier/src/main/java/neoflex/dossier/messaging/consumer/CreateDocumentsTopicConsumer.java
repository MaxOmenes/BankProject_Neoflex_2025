package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.CreateDocumentsTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "create-documents")
public class CreateDocumentsTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final CreateDocumentsTopicHandlerService createDocumentsTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        createDocumentsTopicHandlerService.handle(message);
    }
}
