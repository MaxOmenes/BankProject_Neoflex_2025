package neoflex.calculator.service.calculate;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.service.offer.OfferService;
import neoflex.calculator.service.prescoring.PreScoringService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateOfferService {

    private final PreScoringService preScoringService;
    private final OfferService offerService;

    public List<LoanOfferDto> calculateOffer(LoanStatementRequestDto request) {
        List<LoanOfferDto> offers = preScoringService.preScore(request); //TODO: offers with calculated rates

        offers.forEach(offerService::calculateOffer);

        return offers;
    }
}
