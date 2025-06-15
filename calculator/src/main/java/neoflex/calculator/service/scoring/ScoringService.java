package neoflex.calculator.service.scoring;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;

public interface ScoringService {
    CreditEntity score(ScoringEntity entity);
}
