package neoflex.calculator.service.offer;

import neoflex.calculator.api.dto.LoanOfferDto;

public interface OfferService {
    void calculateOffer(LoanOfferDto offer);
}
