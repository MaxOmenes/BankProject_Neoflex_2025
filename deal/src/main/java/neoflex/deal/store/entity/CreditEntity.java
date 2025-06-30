package neoflex.deal.store.entity;

import jakarta.persistence.*;
import lombok.*;
import neoflex.deal.api.dto.PaymentScheduleElementDto;
import neoflex.deal.store.enums.credit.CreditStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID creditId;

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    @JdbcTypeCode(SqlTypes.JSON)
    @ToString.Exclude
    private List<PaymentScheduleElementDto> paymentSchedule;

    private Boolean insuranceEnabled;

    private Boolean salaryClient;

    private CreditStatus creditStatus;
}
