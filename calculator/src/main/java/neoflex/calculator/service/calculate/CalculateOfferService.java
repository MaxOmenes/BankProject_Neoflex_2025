package neoflex.calculator.service.calculate;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.api.factory.LoanOfferDtoFactory;
import neoflex.calculator.api.factory.LoanStatementRequestDtoFactory;
import neoflex.calculator.service.OfferService;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateOfferService {

    private final LoanStatementRequestDtoFactory loanStatementRequestDtoFactory;
    private final LoanOfferDtoFactory loanOfferDtoFactory;
    private final OfferService offerService;

    public List<LoanOfferDto> calculateOffer(LoanStatementRequestDto request) {
        LoanStatementRequestEntity statement =
                loanStatementRequestDtoFactory.toEntity(request);
        List<OfferEntity> offers = offerService.makeOffers(statement);

        return (List<LoanOfferDto>) offers.stream()
                .map(loanOfferDtoFactory::toDto)
                .toList();
    }
}
