package neoflex.gateway.service.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatementService {
    private final RestClient statementRestClient;

    @Value("${service.statement.path.statement}")
    private String statementEndpoint;
    @Value("${service.statement.path.select-offer}")
    private String selectOfferEndpoint;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request) {
        log.info("Send request to statement service for loan offers: {}", request);
        List<LoanOfferDto> response = statementRestClient.post()
                .uri(statementEndpoint)
                .body(request).retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
        log.info("Received loan offers from statement service: {}", response);
        return response;
    }

    public void selectOffer(LoanOfferDto request) {
        log.info("Received request to statement service to select offer: {}", request);
        statementRestClient.post()
                .uri(selectOfferEndpoint)
                .body(request)
                .retrieve().toBodilessEntity(); //TODO: CHECK HOW
        log.info("Request sent to statement service to select offer: {}", request);
    }

}
