package neoflex.deal.store.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private ClientEntity client;

    @OneToOne
    @JoinColumn(name = "credit_id", nullable = true)
    @ToString.Exclude
    @Nullable
    private CreditEntity credit;

    @Nullable
    private ApplicationStatus status;

    private LocalDate creationDate;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    private LoanOfferDto appliedOffer;

    @Nullable
    private LocalDate signDate; //TODO: in spec it`s time, maybe change

    @Nullable
    private String sesCode;//CODE TO SEND IN EMAIL

    @JdbcTypeCode(SqlTypes.JSON)
    @ToString.Exclude
    private List<StatementStatusHistoryDto> statusHistory;
}
