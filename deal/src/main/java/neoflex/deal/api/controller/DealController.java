package neoflex.deal.api.controller;

import lombok.AllArgsConstructor;
import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.service.local.endpoint.CalculateCreditService;
import neoflex.deal.service.local.endpoint.CalculateOffersService;
import neoflex.deal.service.local.endpoint.SelectOfferService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deal")
@AllArgsConstructor
public class DealController {
    private final CalculateCreditService calculateCreditService;
    private final CalculateOffersService calculateOffersService;
    private final SelectOfferService selectOfferService;

    @RequestMapping("/statement")
    public List<LoanOfferDto> calculateOffers(@RequestBody LoanStatementRequestDto request){
        return calculateOffersService.makeOffers(request);
    }

    @RequestMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto request){
        selectOfferService.selectOffer(request);
    }

    @RequestMapping("/calculate/{statementId}")
    public void calculateCredit(@PathVariable String statementId, @RequestBody FinishRegistrationRequestDto request){
        calculateCreditService.calculateCredit(statementId, request);
    }
}
