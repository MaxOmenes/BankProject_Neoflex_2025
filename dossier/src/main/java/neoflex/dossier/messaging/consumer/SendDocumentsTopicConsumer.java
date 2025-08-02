package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.SendDocumentsTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics="send-documents")
public class SendDocumentsTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final SendDocumentsTopicHandlerService sendDocumentsTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        sendDocumentsTopicHandlerService.handle(message);
    }
}
