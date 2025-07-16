package neoflex.deal.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Value("${service.calculator.url}")
    private String calculatorBaseUrl;

    @Bean
    public RestClient calculatorRestClient() {
        return RestClient.builder()
                .baseUrl(calculatorBaseUrl)
                .build();
    }
}
