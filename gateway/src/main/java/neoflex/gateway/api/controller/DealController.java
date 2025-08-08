package neoflex.gateway.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.gateway.api.dto.FinishRegistrationRequestDto;
import neoflex.gateway.api.dto.LoanOfferDto;
import neoflex.gateway.api.dto.LoanStatementRequestDto;
import neoflex.gateway.service.remote.DealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/deal")
@Tag(name = "Deal MS", description = "Deal API for loan offers and credit calculations")
public class DealController {
    private final DealService dealService;

    @Operation(
            summary = "Calculate loan offers",
            description = "Calculates and returns potential loan offers based on client information and requested loan parameters")
    @PostMapping("/statement")
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto request) {
        log.info("Received request to calculate loan offers: {}", request);
        List<LoanOfferDto> offers = dealService.calculateOffers(request);
        log.info("Recieved response with loan offers: {}", offers);
        return offers;
    }

    @Operation(
            summary = "Select a loan offer",
            description = "Selects a specific loan offer from the available options. **WARNING: You must use a valid statement UUID in statementId from your own application.**"
    )
    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto request) {
        log.info("Received request to select loan offer: {}", request);
        dealService.selectOffer(request);
        log.info("Selected loan offer: {}", request);
    }

    @Operation(
            summary = "Calculate final credit parameters",
            description = "Calculates final credit parameters based on additional client information. Requires a valid statement ID."
    )
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@PathVariable String statementId, @RequestBody FinishRegistrationRequestDto request) {
        log.info("Received request to calculate credit for statementId: {}, request: {}", statementId, request);
        dealService.calculateCredit(statementId, request);
        log.info("Credit calculation initiated for statementId: {}", statementId);
    }

    @Operation(
            summary = "Send documents",
            description = "Send documents to clients email"
    )
    @PostMapping("/document/send/{statementId}")
    public void sendDocuments(@PathVariable String statementId) {
        log.info("Received request to send documents for statementId: {}", statementId);
        dealService.sendDocuments(statementId);
        log.info("Documents sent for statementId: {}", statementId);
    }

    @Operation(
            summary = "Send SES code",
            description = "Send SES code to clients email"
    )
    @PostMapping("/document/code/{statementId}")
    public void sendSesCode(@PathVariable String statementId) {
        log.info("Received request to send SES code for statementId: {}", statementId);
        dealService.sendSesCode(statementId);
        log.info("SES code sent for statementId: {}", statementId);
    }

    @Operation(
            summary = "Send sign notification",
            description = "Send notification that documents are signed"
    )
    @PostMapping("/document/sign/{statementId}")
    public void signDocuments(@PathVariable String statementId,
                              @RequestParam(defaultValue = "false") boolean refused,
                              @RequestParam(required = true) String sesCode) {
        log.info("Received request to sign documents for statementId: {}", statementId);
        dealService.signDocuments(statementId, refused, sesCode);
        log.info("Documents signed for statementId: {}", statementId);
    }
}
