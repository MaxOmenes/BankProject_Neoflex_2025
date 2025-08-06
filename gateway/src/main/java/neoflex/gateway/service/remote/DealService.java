package neoflex.gateway.service.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.FinishRegistrationRequestDto;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DealService {
    private final RestClient dealRestClient;

    @Value("${service.deal.path.statement}")
    private String statementEndpoint;
    @Value("${service.deal.path.select-offer}")
    private String selectOfferEndpoint;
    @Value("${service.deal.path.calculate}")
    private String calculateEndpoint;

    @Value("${service.deal.path.send-documents}")
    private String sendDocumentsEndpoint;
    @Value("${service.deal.path.send-ses-code}")
    private String sendSesCodeEndpoint;
    @Value("${service.deal.path.sign-documents}")
    private String signDocumentsEndpoint;

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto request) {
        log.info("Send request to deal service for loan offers: {}", request);
        List<LoanOfferDto> response = dealRestClient.post()
                .uri(statementEndpoint)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
        log.info("Received loan offers from deal service: {}", response);
        return response;
    }

    public void selectOffer(LoanOfferDto request) {
        log.info("Send request to deal service for select offer: {}", request);
        dealRestClient.post()
                .uri(selectOfferEndpoint)
                .body(request)
                .retrieve(); //TODO: CHECK HOW
        log.info("Selected offer in deal service: {}", request);
    }

    public void calculateCredit(String statementId,
                                FinishRegistrationRequestDto request){
        log.info("Received request to deal service to calculate credit: {}", request);
        dealRestClient.post()
                .uri(calculateEndpoint, statementId)
                .body(request)
                .retrieve(); //TODO: CHECK HOW
        log.info("Request sent to deal service to calculate credit: {}", request);
    }

    public void sendDocuments(String statementId){
        log.info("Received request to deal service to send documents for statementId: {}", statementId);
        dealRestClient.post()
                .uri(sendDocumentsEndpoint, statementId)
                .retrieve(); //TODO: CHECK HOW
        log.info("Sent documents for statementId: {}", statementId);
    }

    public void sendSesCode(String statementId) {
        log.info("Received request to deal service to send SES code for statementId: {}", statementId);
        dealRestClient.post()
                .uri(sendSesCodeEndpoint, statementId)
                .retrieve(); //TODO: CHECK HOW
        log.info("Sent SES code for statementId: {}", statementId);
    }

    public void signDocuments(String statementId, boolean refused, String sesCode) {
        log.info("Received request to deal service to sign documents for statementId: {}", statementId);
        dealRestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(signDocumentsEndpoint)
                        .queryParam("refused", refused)
                        .queryParam("sesCode", sesCode)
                        .build(statementId))
                .retrieve(); //TODO: CHECK HOW
        log.info("Signed documents for statementId: {}", statementId);
    }
}
