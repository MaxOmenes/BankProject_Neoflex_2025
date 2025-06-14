package neoflex.calculator.service;

import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;

import java.util.List;

public interface OfferService {
    public List<OfferEntity> makeOffers(LoanStatementRequestEntity loanStatementRequestEntity);
}
