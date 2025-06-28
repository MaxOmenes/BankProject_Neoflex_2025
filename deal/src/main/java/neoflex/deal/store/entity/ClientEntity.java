package neoflex.deal.store.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import neoflex.deal.api.dto.EmploymentDto;
import neoflex.deal.store.enums.client.Gender;
import neoflex.deal.store.enums.client.MaritalStatus;
import neoflex.deal.api.dto.PassportDto;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class ClientEntity {
    @Id
    private UUID clientId;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthDate;

    private String email;

    @Nullable
    private Gender gender;     //TODO: in spec it`s varchar, maybe change to String

    @Nullable
    private MaritalStatus maritalStatus; //TODO: in spec it`s varchar, maybe change to String

    private Integer dependentAmount; //TODO: in spec it`s int, maybe change to BigDecimal

    @JdbcTypeCode(SqlTypes.JSON)
    private PassportDto passport;

    @JdbcTypeCode(SqlTypes.JSON)
    private EmploymentDto employmentDto;

    private String accountNumber;

}
