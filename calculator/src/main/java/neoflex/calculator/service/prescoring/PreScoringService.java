package neoflex.calculator.service.prescoring;

import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;

import java.util.List;

public interface PreScoringService {
    List<LoanOfferDto> preScore(LoanStatementRequestDto loanStatementRequestDto);
}
