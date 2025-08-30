package neoflex.calculator.service.credit;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.PaymentScheduleElementDto;
import neoflex.calculator.service.credit.payment.PaymentService;
import neoflex.calculator.service.insurance.InsuranceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalculateAnnuityCreditService implements CreditService{

    private final PaymentService paymentService;
    private final InsuranceService insuranceService;


    private final String AAA = "AAA";

    public void test(String a){
        if(AAA.equals(a)){
            System.out.println("BBB");
        }
    }

    @Override
    public void calculateCredit(CreditDto credit) {

        paymentService.calculatePaymentSchedule(credit);

        BigDecimal psk = credit.getPaymentSchedule().stream()
                .map(PaymentScheduleElementDto::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);



        List<BigDecimal> insurancePayments = insuranceService.calculateInsurance(credit);

        BigDecimal totalInsurance = insurancePayments.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        credit.setPsk(psk.add(totalInsurance).setScale(2, RoundingMode.HALF_UP));
    }
}
