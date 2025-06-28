package neoflex.deal.store.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import neoflex.deal.api.dto.EmploymentDto;
import neoflex.deal.store.enums.client.Gender;
import neoflex.deal.store.enums.client.MaritalStatus;
import neoflex.deal.api.dto.PassportDto;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthdate;

    private String email;

    @Nullable
    private Gender gender;     //TODO: in spec it`s varchar, maybe change to String

    @Nullable
    private MaritalStatus maritalStatus; //TODO: in spec it`s varchar, maybe change to String

    private Integer dependentAmount; //TODO: in spec it`s int, maybe change to BigDecimal

    @JdbcTypeCode(SqlTypes.JSON)
    @ToString.Exclude
    private PassportDto passport;


    @JdbcTypeCode(SqlTypes.JSON)
    @ToString.Exclude
    private EmploymentDto employmentDto;

    private String accountNumber;

}
