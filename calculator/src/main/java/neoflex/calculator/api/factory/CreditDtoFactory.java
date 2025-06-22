package neoflex.calculator.api.factory;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.store.entity.credit.CreditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditDtoFactory implements EntityToDtoFactory<CreditDto, CreditEntity>{

    private final PaymentScheduleDtoFactory paymentScheduleDtoFactory;

    @Override
    public CreditDto toDto(CreditEntity entity) {
        return CreditDto.builder()
                .amount(entity.getAmount())
                .term(entity.getTerm())
                .rate(entity.getRate())
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
