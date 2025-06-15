package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.PaymentScheduleElementDto;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentScheduleDtoFactory implements EntityToDtoFactory<PaymentScheduleElementDto, PaymentScheduleEntity>{
    @Override
    public PaymentScheduleElementDto toDto(PaymentScheduleEntity entity) {
        return PaymentScheduleElementDto.builder()
                .number(entity.getNumber())
                .date(entity.getDate())
                .totalPayment(entity.getTotalPayment())
                .interestPayment(entity.getInterestPayment())
                .debtPayment(entity.getDebtPayment())
                .remainingDebt(entity.getRemainingDebt())
                .build();
    }
}
