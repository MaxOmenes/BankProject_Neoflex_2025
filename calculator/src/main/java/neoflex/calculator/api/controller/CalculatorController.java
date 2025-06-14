package neoflex.calculator.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.api.factory.LoanOfferDtoFactory;
import neoflex.calculator.api.factory.LoanStatementRequestDtoFactory;
import neoflex.calculator.service.OfferService;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Calculator", description = "API for calculating loan offers and credit scoring")
public class CalculatorController {

    LoanStatementRequestDtoFactory loanStatementRequestDtoFactory;
    LoanOfferDtoFactory loanOfferDtoFactory;
    OfferService offerService;

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
        LoanStatementRequestEntity statement =
                loanStatementRequestDtoFactory.toEntity(request);
        List<OfferEntity> offers = offerService.makeOffers(statement);
        return offers.stream()
                .map(loanOfferDtoFactory::toDto)
                .toList();
    }

    @Operation(
            summary = "Calculate credit",
            description = "Validation of submitted data + data scoring + full calculation of credit parameters")
    @PostMapping("calc")
    public CreditDto calculateCredit(ScoringDataDto scoringData) {
        return null;
    }
}
