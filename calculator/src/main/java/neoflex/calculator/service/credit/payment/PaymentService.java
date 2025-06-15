package neoflex.calculator.service.credit.payment;

import neoflex.calculator.store.entity.credit.CreditEntity;

public interface PaymentService {
    void calculatePaymentSchedule(CreditEntity entity);
}
