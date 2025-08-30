package neoflex.deal.configuration.messaging.topics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/***
 * Read topics configuration from application properties.
 * <p>
 * Example:
 * <a href="https://stackoverflow.com/questions/56770412/creating-multiple-kafka-topics-using-spring">
 *     Creating multiple Kafka topics using Spring
 * </a>
 */
@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "messaging.topics")
public class PropertyTopicsConfiguration {

    private List<PropertyTopic> topics = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyTopic {
        private String name;
        private Integer partitions;
        private Integer replicas;
    }
}
