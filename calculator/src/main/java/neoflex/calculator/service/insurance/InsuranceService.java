package neoflex.calculator.service.insurance;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.offer.OfferEntity;

import java.math.BigDecimal;
import java.util.List;

public interface InsuranceService {
    List<BigDecimal> calculateInsurance(OfferEntity entity);
    List<BigDecimal> calculateInsurance(CreditEntity entity);
}
