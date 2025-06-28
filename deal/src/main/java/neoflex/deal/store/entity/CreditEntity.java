package neoflex.deal.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import neoflex.deal.api.dto.PaymentScheduleElementDto;
import neoflex.deal.store.enums.credit.CreditStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;
@Entity
public class CreditEntity {
    @Id
    private UUID creditId;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    @JdbcTypeCode(SqlTypes.JSON)
    private PaymentScheduleElementDto paymentSchedule;
    private Boolean insuranceEnabled;
    private Boolean salaryClient;
    private CreditStatus creditStatus;
}
