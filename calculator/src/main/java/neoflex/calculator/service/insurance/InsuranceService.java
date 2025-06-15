package neoflex.calculator.service.insurance;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;

import java.math.BigDecimal;
import java.util.List;

public interface InsuranceService {
    public List<BigDecimal> calculateInsurance(OfferEntity entity);
    //TODO: add method that i will use in calculate credit service
    public List<BigDecimal> calculateInsurance(CreditEntity entity);
}
