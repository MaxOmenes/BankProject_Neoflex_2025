package neoflex.calculator.service.insurance;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;

import java.math.BigDecimal;
import java.util.List;

public interface InsuranceService {
    public List<BigDecimal> calculateInsurance(OfferEntity entity);
    public List<BigDecimal> calculateInsurance(CreditEntity entity);
}
