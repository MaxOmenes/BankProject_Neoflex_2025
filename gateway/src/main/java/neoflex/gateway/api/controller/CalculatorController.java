package neoflex.gateway.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.CreditDto;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.api.dto.ScoringDataDto;
import neoflex.gateway.service.remote.CalculatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto request) {
        log.info("Calculate offers request recieved: {}", request);
        List<LoanOfferDto> offers = calculatorService.calculateLoanOffers(request);
        log.info("Calculate offers response recieved: {}", offers);
        return offers;
    }

    @PostMapping("/calc")
    public CreditDto calculcateCredit(@RequestBody ScoringDataDto request){
        log.info("Calculate credit request recieved: {}", request);
        CreditDto credit = calculatorService.calculateCredit(request);
        log.info("Calculate credit response recieved: {}", credit);
        return credit;
    }
}
