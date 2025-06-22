package neoflex.calculator.service.calculate;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.api.exception.ValidationException;
import neoflex.calculator.api.factory.CreditDtoFactory;
import neoflex.calculator.api.factory.ScoringDataDtoFactory;
import neoflex.calculator.service.credit.CreditService;
import neoflex.calculator.service.scoring.ScoringService;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateCreditService {

    private final CreditDtoFactory creditDtoFactory;
    private final ScoringDataDtoFactory scoringDataDtoFactory;
    private final ScoringService scoringService;
    private final CreditService creditService;

    public CreditDto calculateCredit (ScoringDataDto scoringData) {
        // Perform scoring
        ScoringEntity scoringEntity = scoringDataDtoFactory.toEntity(scoringData);
        CreditEntity credit = scoringService.score(scoringEntity);


        if (credit == null) {
            throw new ValidationException("Scoring failed. Please check the provided data.");
        }

        creditService.calculateCredit(credit);
        return creditDtoFactory.toDto(credit);
    }
}
