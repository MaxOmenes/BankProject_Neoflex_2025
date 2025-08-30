package neoflex.statement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Value("${service.deal.url}")
    private String dealBaseUrl;

    @Bean
    public RestClient calculatorRestClient() {
        return RestClient.builder()
                .baseUrl(dealBaseUrl)
                .build();
    }
}
