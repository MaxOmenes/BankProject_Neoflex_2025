package neoflex.calculator.api.controller;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request) {
        return null;
    }

    @PostMapping("calc")
    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        return null;
    }
}
