package neoflex.calculator.service.scoring;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.ScoringDataDto;

public interface ScoringService {
    CreditDto score(ScoringDataDto score);
}
