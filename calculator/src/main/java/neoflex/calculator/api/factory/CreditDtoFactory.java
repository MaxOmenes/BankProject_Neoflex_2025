package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.store.entity.credit.CreditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditDtoFactory implements EntityToDtoFactory<CreditDto, CreditEntity>{
    @Autowired
    PaymentScheduleDtoFactory paymentScheduleDtoFactory;

    @Override
    public CreditDto toDto(CreditEntity entity) {
        return CreditDto.builder()
                .amount(entity.getAmount())
                .term(entity.getTerm())
                .monthlyPayment(entity.getMonthlyPayment())
                .psk(entity.getPsk())
                .isInsuranceEnabled(entity.getIsInsuranceEnabled())
                .isSalaryClient(entity.getIsSalaryClient())
                .paymentSchedule(entity.getPaymentSchedule().stream().map(
                        paymentScheduleDtoFactory::toDto
                ).toList())
                .build();
    }
}
