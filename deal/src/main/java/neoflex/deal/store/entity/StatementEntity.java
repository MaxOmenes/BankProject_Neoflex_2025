package neoflex.deal.store.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class StatementEntity {
    @Id
    private UUID statementId;

    @OneToOne @MapsId
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToOne @MapsId
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;

    @Nullable
    private ApplicationStatus status;

    private LocalDate creationDate;

    @JdbcTypeCode(SqlTypes.JSON)
    private LoanOfferDto appliedOffer;

    private LocalDate signDate; //TODO: in spec it`s time, maybe change

    private String sesCode;//CODE TO SEND IN EMAIL

    @JdbcTypeCode(SqlTypes.JSON)
    private List<StatementStatusHistoryDto> statusHistory;
}
