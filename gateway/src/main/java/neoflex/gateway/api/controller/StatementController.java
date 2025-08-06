package neoflex.gateway.api.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.service.remote.StatementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/statement")
public class StatementController {
    private final StatementService statementService;

    @PostMapping
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request){
        log.info("Received request to statement service to calculate offers: {}", request);
        List<LoanOfferDto> offers = statementService.calculateLoanOffers(request);
        log.info("Request sent to statement service to calculate offers: {}", request);
        return offers;
    }

    @PostMapping("/offer")
    public void selectOffer(LoanOfferDto request){
        log.info("Received request to statement service to select offer: {}", request);
        statementService.selectOffer(request);
        log.info("Request sent to statement service to select offer: {}", request);
    }
}
