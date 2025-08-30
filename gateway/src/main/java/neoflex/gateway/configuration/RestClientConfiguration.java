package neoflex.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Value("${service.calculator.url}")
    private String calculatorBaseUrl;

    @Value("${service.statement.url}")
    private String statementBaseUrl;

    @Value("${service.deal.url}")
    private String dealBaseUrl;

    @Bean
    public RestClient calculatorRestClient() {
        return RestClient.builder()
                .baseUrl(calculatorBaseUrl)
                .build();
    }

    @Bean
    public RestClient statementRestClient() {
        return RestClient.builder()
                .baseUrl(statementBaseUrl)
                .build();
    }

    @Bean
    public RestClient dealRestClient() {
        return RestClient.builder()
                .baseUrl(dealBaseUrl)
                .build();
    }
}
