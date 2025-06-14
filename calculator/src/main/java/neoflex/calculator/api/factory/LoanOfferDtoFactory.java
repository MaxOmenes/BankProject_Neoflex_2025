package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.store.entity.offer.OfferEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanOfferDtoFactory implements EntityToDtoFactory<LoanOfferDto, OfferEntity> {

    @Override
    public LoanOfferDto toDto(OfferEntity entity) {
        return LoanOfferDto.builder()
                .statementId(entity.getStatementId())
                .requestedAmount(entity.getRequestedAmount())
                .totalAmount(entity.getTotalAmount())
                .term(entity.getTerm())
                .monthlyPayment(entity.getMonthlyPayment())
                .rate(entity.getRate())
                .isInsuranceEnabled(entity.getIsInsuranceEnabled())
                .isSalaryClient(entity.getIsSalaryClient())
                .build();
    }
}
