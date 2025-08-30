package neoflex.gateway.service.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.CreditDto;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.api.dto.ScoringDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculatorService {
    private final RestClient calculatorRestClient;

    @Value("${service.calculator.path.offers}")
    private String offersEndpoint;
    @Value("${service.calculator.path.calculate}")
    private String calculateEndpoint;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request) {
        log.info("Send request to calculator service for loan offers: {}", request);
        List<LoanOfferDto> response = calculatorRestClient.post()
                .uri(offersEndpoint)
                .body(request).retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
        log.info("Received loan offers from calculator service: {}", response);
        return response;
    }

    public CreditDto calculateCredit(ScoringDataDto request) {
        log.info("Send request to calculator service for calculate credit: {}", request);
        CreditDto response = calculatorRestClient.post()
                .uri(calculateEndpoint)
                .body(request)
                .retrieve()
                .body(CreditDto.class);
        log.info("Received credit from calculator service: {}", response);
        return response;
    }

}
