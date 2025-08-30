package neoflex.deal.service.remote.calculator;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import neoflex.deal.api.dto.CreditDto;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.api.dto.ScoringDataDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculatorService {
    @Value("${service.calculator.path.offers}")
    String offersEndpoint;
    @Value("${service.calculator.path.calculate}")
    String calcEndpoint;

    private final RestClient calculatorRestClient;

    public List<LoanOfferDto> offers(LoanStatementRequestDto statementRequest){

        return calculatorRestClient.post()
                .uri(offersEndpoint)
                .body(statementRequest)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
    }

    public CreditDto calc(ScoringDataDto scoringData) {
        return calculatorRestClient.post()
                .uri(calcEndpoint)
                .body(scoringData)
                .retrieve()
                .body(CreditDto.class);
    }
}
