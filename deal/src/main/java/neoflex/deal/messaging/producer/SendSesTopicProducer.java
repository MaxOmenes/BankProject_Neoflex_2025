package neoflex.deal.messaging.producer;

import lombok.RequiredArgsConstructor;
import neoflex.deal.configuration.messaging.topics.enums.Topic;
import neoflex.deal.messaging.dto.EmailMessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendSesTopicProducer implements TopicProducer<EmailMessageDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(EmailMessageDto message) {
        kafkaTemplate.send(Topic.SEND_SES.getName(), message.getStatementId().toString(), message);

    }
}
