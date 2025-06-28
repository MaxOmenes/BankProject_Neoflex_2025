package neoflex.deal.service.remote.calculator;

import lombok.AllArgsConstructor;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@AllArgsConstructor
public class CalculatorService {
    private final RestClient calculatorRestClient;

    public List<LoanOfferDto> offers(LoanStatementRequestDto statementRequest){

        return calculatorRestClient.post()
                .uri("/offers")
                .body(statementRequest)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
    }
}
