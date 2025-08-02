package neoflex.deal.configuration.messaging.kafka;

import lombok.RequiredArgsConstructor;
import neoflex.deal.configuration.messaging.topics.PropertyTopicsConfiguration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicsConfiguration {

    private final PropertyTopicsConfiguration topicsConfiguration;

    @Bean
    public KafkaAdmin.NewTopics createTopics() {
        List<NewTopic> topics = new ArrayList<>();

        topicsConfiguration.getTopics()
                .forEach(topic -> topics.add(
                        TopicBuilder.name(topic.getName())
                                .partitions(topic.getPartitions())
                                .replicas(topic.getReplicas())
                                .build()
                        )
                );

        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
    }
}
