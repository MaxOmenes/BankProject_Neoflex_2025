package neoflex.statement.service.local.endpoint;

import lombok.RequiredArgsConstructor;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.service.remote.deal.DealService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelectOfferService {
    private final DealService dealService;

    public void selectOffer(LoanOfferDto offer) {
        dealService.selectOffer(offer);
    }
}
