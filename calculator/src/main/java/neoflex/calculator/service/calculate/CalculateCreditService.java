package neoflex.calculator.service.calculate;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.api.exception.ValidationException;
import neoflex.calculator.service.credit.CreditService;
import neoflex.calculator.service.scoring.ScoringService;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateCreditService {

    private final ScoringService scoringService;
    private final CreditService creditService;

    public CreditDto calculateCredit (ScoringDataDto scoringData) {
        CreditDto credit = scoringService.score(scoringData);


        if (credit == null) {
            throw new ValidationException("Scoring failed. Please check the provided data.");
        }

        creditService.calculateCredit(credit);
        return credit;
    }
}
