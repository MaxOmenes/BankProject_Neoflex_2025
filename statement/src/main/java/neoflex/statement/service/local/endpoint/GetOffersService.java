package neoflex.statement.service.local.endpoint;

import lombok.RequiredArgsConstructor;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import neoflex.statement.service.remote.deal.DealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOffersService {
    private final DealService dealService;

    public List<LoanOfferDto> getOffers(LoanStatementRequestDto request) {
        return dealService.getOffers(request);
    }
}
