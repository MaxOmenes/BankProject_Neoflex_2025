package neoflex.gateway.api.controller;

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
public class DealController {
    private final DealService dealService;

    @PostMapping("/statement")
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto request) {
        log.info("Received request to calculate loan offers: {}", request);
        List<LoanOfferDto> offers = dealService.calculateOffers(request);
        log.info("Recieved response with loan offers: {}", offers);
        return offers;
    }

    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto request) {
        log.info("Received request to select loan offer: {}", request);
        dealService.selectOffer(request);
        log.info("Selected loan offer: {}", request);
    }

    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@PathVariable String statementId, @RequestBody FinishRegistrationRequestDto request) {
        log.info("Received request to calculate credit for statementId: {}, request: {}", statementId, request);
        dealService.calculateCredit(statementId, request);
        log.info("Credit calculation initiated for statementId: {}", statementId);
    }

    @PostMapping("/document/send/{statementId}")
    public void sendDocuments(@PathVariable String statementId) {
        log.info("Received request to send documents for statementId: {}", statementId);
        dealService.sendDocuments(statementId);
        log.info("Documents sent for statementId: {}", statementId);
    }

    @PostMapping("/document/code/{statementId}")
    public void sendSesCode(@PathVariable String statementId) {
        log.info("Received request to send SES code for statementId: {}", statementId);
        dealService.sendSesCode(statementId);
        log.info("SES code sent for statementId: {}", statementId);
    }

    @PostMapping("/document/sign/{statementId}")
    public void signDocuments(@PathVariable String statementId,
                              @RequestParam(defaultValue = "false") boolean refused,
                              @RequestParam(required = true) String sesCode) {
        log.info("Received request to sign documents for statementId: {}", statementId);
        dealService.signDocuments(statementId, refused, sesCode);
        log.info("Documents signed for statementId: {}", statementId);
    }
}
