package neoflex.calculator.service.insurance;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.LoanOfferDto;

import java.math.BigDecimal;
import java.util.List;

public interface InsuranceService {
    List<BigDecimal> calculateInsurance(LoanOfferDto offer);
    List<BigDecimal> calculateInsurance(CreditDto credit);
}
