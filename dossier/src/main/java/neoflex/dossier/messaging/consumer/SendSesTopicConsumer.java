package neoflex.dossier.messaging.consumer;

import lombok.RequiredArgsConstructor;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.handler.SendSesTopicHandlerService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics="send-ses")
public class SendSesTopicConsumer implements TopicConsumer<EmailMessageDto> {
    private final SendSesTopicHandlerService sendSesTopicHandlerService;

    @Override
    @KafkaHandler
    public void handle(EmailMessageDto message) {
        sendSesTopicHandlerService.handle(message);
    }
}
