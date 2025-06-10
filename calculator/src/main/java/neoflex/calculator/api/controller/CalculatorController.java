package neoflex.calculator.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Calculator", description = "API for calculating loan offers and credit scoring")
public class CalculatorController {
    @Operation(
            summary = "Calculate loan offers",
            description = "Calculation of possible loan terms")
    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request) {
        return null;
    }

    @Operation(
            summary = "Calculate credit",
            description = "Validation of submitted data + data scoring + full calculation of credit parameters")
    @PostMapping("calc")
    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        return null;
    }
}
