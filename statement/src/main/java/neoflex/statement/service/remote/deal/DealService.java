package neoflex.statement.service.remote.deal;

import lombok.RequiredArgsConstructor;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {
    @Value("${service.deal.path.statement}")
    String statementEndpoint;
    @Value("${service.deal.path.select-offer}")
    String selectOfferEndpoint;

    private final RestClient restClient;
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto request){
        return restClient.post()
                .uri(statementEndpoint)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
        });
    }

    public void selectOffer(LoanOfferDto offer) {
        restClient.post()
                .uri(selectOfferEndpoint)
                .body(offer)
                .retrieve()
                .body(Void.class);
    }
}
