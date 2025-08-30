package neoflex.gateway.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.CreditDto;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.api.dto.ScoringDataDto;
import neoflex.gateway.service.remote.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calculator")
@Tag(name = "Calculator MS", description = "Calculator API for loan offers and credit calculations")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Operation(
            summary = "Calculate loan offers",
            description = "Calculation of possible loan terms"
    )
    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto request) {
        log.info("Calculate offers request recieved: {}", request);
        List<LoanOfferDto> offers = calculatorService.calculateLoanOffers(request);
        log.info("Calculate offers response recieved: {}", offers);
        return offers;
    }

    @Operation(
            summary = "Calculate credit",
            description = "Validation of submitted data + data scoring + full calculation of credit parameters")
    @PostMapping("/calc")
    public CreditDto calculateCredit(@RequestBody ScoringDataDto request){
        log.info("Calculate credit request recieved: {}", request);
        CreditDto credit = calculatorService.calculateCredit(request);
        log.info("Calculate credit response recieved: {}", credit);
        return credit;
    }
}
