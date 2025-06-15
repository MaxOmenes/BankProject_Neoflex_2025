package neoflex.calculator.service.credit;

import neoflex.calculator.service.credit.payment.PaymentService;
import neoflex.calculator.service.insurance.InsuranceService;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.credit.PaymentScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculateAnnuityCreditService implements CreditService{
    @Autowired
    PaymentService paymentService;
    @Autowired
    InsuranceService insuranceService;

    @Override
    public void calculateCredit(CreditEntity entity) {

        paymentService.calculatePaymentSchedule(entity);

        BigDecimal psk = entity.getPaymentSchedule().stream()
                .map(PaymentScheduleEntity::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<BigDecimal> insurancePayments = insuranceService.calculateInsurance(entity);

        BigDecimal totalInsurance = insurancePayments.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        entity.setPsk(psk.add(totalInsurance).setScale(2, RoundingMode.HALF_UP));
    }
}
