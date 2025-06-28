package neoflex.deal.service.local.endpoint;

import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateOffersService {
    public List<LoanOfferDto> makeOffers(LoanStatementRequestDto statementRequest){
        return null; //TODO: implement logic to make offers based on the statement request
    }
}
