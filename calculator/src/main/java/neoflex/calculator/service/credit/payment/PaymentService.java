package neoflex.calculator.service.credit.payment;

import neoflex.calculator.api.dto.CreditDto;

public interface PaymentService {
    void calculatePaymentSchedule(CreditDto credit);
}
