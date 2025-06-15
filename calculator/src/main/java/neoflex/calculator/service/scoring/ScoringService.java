package neoflex.calculator.service.scoring;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;

public interface ScoringService {
    //TODO: edit return type and logic
    CreditEntity score(ScoringEntity entity);
}
