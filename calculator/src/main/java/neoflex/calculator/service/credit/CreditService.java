package neoflex.calculator.service.credit;

import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;

public interface CreditService {

    public void calculateCredit(CreditEntity entity);
}
