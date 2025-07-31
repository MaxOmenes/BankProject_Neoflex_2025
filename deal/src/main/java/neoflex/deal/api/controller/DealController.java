package neoflex.deal.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.service.local.endpoint.calculate.CalculateCreditService;
import neoflex.deal.service.local.endpoint.document.SendDocumentsService;
import neoflex.deal.service.local.endpoint.document.SignDocumentsService;
import neoflex.deal.service.local.endpoint.document.SendSesCodeService;
import neoflex.deal.service.local.endpoint.statement.CalculateOffersService;
import neoflex.deal.service.local.endpoint.offer.SelectOfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {
    private final CalculateCreditService calculateCreditService;
    private final CalculateOffersService calculateOffersService;
    private final SelectOfferService selectOfferService;

    private final SendDocumentsService sendDocumentsService;
    private final SendSesCodeService sendSesCodeService;
    private final SignDocumentsService signDocumentsService;

    @Operation(
            summary = "Calculate loan offers",
            description = "Calculates and returns potential loan offers based on client information and requested loan parameters")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoanStatementRequestDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "amount": 100000,
                                      "term": 36,
                                      "firstName": "Snake",
                                      "lastName": "Solid",
                                      "middleName": "John",
                                      "email": "venom.snake@diamonddogs.com",
                                      "birthdate": "1972-01-01",
                                      "passportSeries": "1234",
                                      "passportNumber": "567890"
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/statement")
    public List<LoanOfferDto> calculateOffers(@RequestBody LoanStatementRequestDto request) {
        return calculateOffersService.makeOffers(request);
    }

    @Operation(
            summary = "Select a loan offer",
            description = "Selects a specific loan offer from the available options. **WARNING: You must use a valid statement UUID in statementId from your own application.**"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoanOfferDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "statementId": "43435860-be1c-4879-9c63-e28aeaecc791",
                                        "requestedAmount": 100000,
                                        "totalAmount": 158901.76067367982,
                                        "term": 36,
                                        "monthlyPayment": 4383.24057,
                                        "rate": 32.5,
                                        "isInsuranceEnabled": true,
                                        "isSalaryClient": true
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto request) {
        selectOfferService.selectOffer(request);
    }

    @Operation(
            summary = "Calculate final credit parameters",
            description = "Calculates final credit parameters based on additional client information. Requires a valid statement ID."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FinishRegistrationRequestDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "gender": "MALE",
                                      "maritalStatus": "MARRIED",
                                      "dependentAmount": 2,
                                      "passportIssueDate": "2025-06-28",
                                      "passportIssueBranch": "U.S. Department of State",
                                      "employment": {
                                        "employmentStatus": "EMPLOYED",
                                        "employerINN": "1234567890",
                                        "salary": 50000,
                                        "position": "TOP_MANAGER",
                                        "workExperienceTotal": 120,
                                        "workExperienceCurrent": 24
                                      },
                                      "accountNumber": "12345678901234567890"
                                    }
                                    """
                    )
            )
    )
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@PathVariable String statementId,
                                @RequestBody FinishRegistrationRequestDto request) {
        calculateCreditService.calculateCredit(statementId, request);
    }

    @Operation(
            summary = "Send documents",
            description = "Send documents to clients email"
    )
    @PostMapping("/document/{statementId}/send")
    public void sendDocument(@PathVariable String statementId) {
        sendDocumentsService.sendDocuments(statementId);
    }

    @Operation(
            summary = "Send SES code",
            description = "Send SES code to clients email"
    )
    @PostMapping("/document/{statementId}/code")
    public void sendSes(@PathVariable String statementId) {
        //TODO: REJECT LOGIC
        sendSesCodeService.sendSes(statementId);
    }

    @Operation(
            summary = "Send sign notification",
            description = "Send notification that documents are signed"
    )
    @PostMapping("/document/{statementId}/sign")
    public void signDocument(@PathVariable String statementId, @RequestParam(defaultValue = "false") boolean refused) {
        signDocumentsService.signDocuments(statementId, refused);
    }

}
