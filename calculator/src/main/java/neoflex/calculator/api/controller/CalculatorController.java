package neoflex.calculator.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.api.exception.ValidationException;
import neoflex.calculator.api.factory.CreditDtoFactory;
import neoflex.calculator.api.factory.LoanOfferDtoFactory;
import neoflex.calculator.api.factory.LoanStatementRequestDtoFactory;
import neoflex.calculator.api.factory.ScoringDataDtoFactory;
import neoflex.calculator.service.OfferService;
import neoflex.calculator.service.credit.CreditService;
import neoflex.calculator.service.scoring.ScoringService;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;
import org.springframework.beans.factory.annotation.Value;
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

    private final LoanStatementRequestDtoFactory loanStatementRequestDtoFactory;
    private final LoanOfferDtoFactory loanOfferDtoFactory;
    private final OfferService offerService;

    private final CreditDtoFactory creditDtoFactory;
    private final ScoringDataDtoFactory scoringDataDtoFactory;
    private final ScoringService scoringService;
    private final CreditService creditService;

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
        LoanStatementRequestEntity statement =
                loanStatementRequestDtoFactory.toEntity(request);
        List<OfferEntity> offers = offerService.makeOffers(statement);
        List<LoanOfferDto> offersDto = (List<LoanOfferDto>) offers.stream()
                .map(loanOfferDtoFactory::toDto)
                .toList();
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
        ScoringEntity scoringEntity = scoringDataDtoFactory.toEntity(scoringData);
        CreditEntity credit = scoringService.score(scoringEntity);


        if (credit == null) {
            throw new ValidationException("Scoring failed. Please check the provided data.");
        }

        creditService.calculateCredit(credit);
        CreditDto creditDto = creditDtoFactory.toDto(credit);
        log.info("Calculated credit: {}", creditDto);
        return creditDto;
    }
}
