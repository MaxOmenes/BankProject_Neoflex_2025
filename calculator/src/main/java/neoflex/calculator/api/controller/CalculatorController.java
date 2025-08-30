package neoflex.calculator.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.service.calculate.CalculateCreditService;
import neoflex.calculator.service.calculate.CalculateOfferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Tag(name = "Calculator", description = "API for calculating loan offers and credit scoring")
@Slf4j
public class CalculatorController {

    private final CalculateOfferService calculateOfferService;
    private final CalculateCreditService calculateCreditService;


    @Operation(
            summary = "Calculate loan offers",
            description = "Calculation of possible loan terms")
    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request for loan statement",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoanStatementRequestDto.class),
                    examples = @ExampleObject(
                            name = "LoanStatementRequestDto Example",
                            value = """
                                    {
                                        "amount": 100000,
                                        "term": 36,
                                        "firstName": "Snake",
                                        "lastName": "Solid",
                                        "middleName": "John",
                                        "email": "solid.snake@foxhound.com",
                                        "birthdate": "1972-01-01",
                                        "passportSeries": "1234",
                                        "passportNumber": "567890"
                                    }
                                    """
                    )
            )
    ) @RequestBody LoanStatementRequestDto request) {
        log.info("Received request for loan offers: {}", request);
        List<LoanOfferDto> offersDto = calculateOfferService.calculateOffer(request);
        log.info("Calculated loan offers: {}", offersDto);
        return offersDto;
    }

    @Operation(
            summary = "Calculate credit",
            description = "Validation of submitted data + data scoring + full calculation of credit parameters")
    @PostMapping("calc")
    public CreditDto calculateCredit(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Scoring data for credit calculation",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "ScoringDataDto Example",
                            value = """
                                    {
                                        "amount": 100000,
                                        "term": 36,
                                        "firstName": "Snake",
                                        "lastName": "Solid",
                                        "middleName": "John",
                                        "gender": "MALE",
                                        "birthdate": "1972-01-01",
                                        "passportSeries": "1234",
                                        "passportNumber": "567890",
                                        "passportIssueDate": "2010-01-01",
                                        "passportIssueBranch": "U.S. Department of State",
                                        "maritalStatus": "MARRIED",
                                        "dependentAmount": 2,
                                        "employment": {
                                            "employmentStatus": "EMPLOYED",
                                            "employerINN": "1234567890",
                                            "salary": 50000,
                                            "position": "MANAGER",
                                            "workExperienceTotal": 120,
                                            "workExperienceCurrent": 24
                                        },
                                        "accountNumber": "12345678901234567890",
                                        "isInsuranceEnabled": true,
                                        "isSalaryClient": true
                                    }
                                    """
                    )
            )
    ) @RequestBody @Valid ScoringDataDto scoringData) {
        log.info("Received scoring data for credit calculation: {}", scoringData);
        CreditDto creditDto = calculateCreditService.calculateCredit(scoringData);
        log.info("Calculated credit: {}", creditDto);
        return creditDto;
    }
}
