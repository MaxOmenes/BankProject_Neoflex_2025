package neoflex.calculator.service;

import neoflex.calculator.service.calculator.CalculateOfferService;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class PreScoringService implements OfferService {
    @Value("${constants.rate}")
    Double rate;

    @Autowired
    private CalculateOfferService calculateOfferService;

    @Override
    public List<OfferEntity> makeOffers(LoanStatementRequestEntity statementRequest) {
        List<OfferEntity> offers = new ArrayList<>();
        for (boolean isInsuranceEnabled : List.of(true, false)){
            for (boolean isSalaryClient : List.of(true, false)){
                OfferEntity offer = OfferEntity.builder()
                        .statementId(statementRequest.getId())
                        .requestedAmount(statementRequest.getAmount())
                        .term(statementRequest.getTerm())
                        .rate(BigDecimal.valueOf(rate))
                        .isInsuranceEnabled(isInsuranceEnabled)
                        .isSalaryClient(isSalaryClient)
                        .build();

                System.out.println(offer.getRate());

                calculateOfferService.calculateOffer(offer);
                offers.add(offer);
            }
        }

        return offers;
    }
}
