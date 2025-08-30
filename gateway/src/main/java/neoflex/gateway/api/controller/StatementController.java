package neoflex.gateway.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.service.remote.StatementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/statement")
@Tag(name = "Statement MS", description = "Statement API for loan offers and credit calculations")
public class StatementController {
    private final StatementService statementService;
    @Operation(
            summary = "Get Loan Offers",
            description = "Retrieves potential loan offers based on the provided loan statement request.")
    @PostMapping
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request){
        log.info("Received request to statement service to calculate offers: {}", request);
        List<LoanOfferDto> offers = statementService.calculateLoanOffers(request);
        log.info("Request sent to statement service to calculate offers: {}", request);
        return offers;
    }

    @Operation(
            summary = "Select Loan Offer",
            description = "Selects a specific loan offer from the available options.")
    @PostMapping("/offer")
    public void selectOffer(LoanOfferDto request){
        log.info("Received request to statement service to select offer: {}", request);
        statementService.selectOffer(request);
        log.info("Request sent to statement service to select offer: {}", request);
    }
}
