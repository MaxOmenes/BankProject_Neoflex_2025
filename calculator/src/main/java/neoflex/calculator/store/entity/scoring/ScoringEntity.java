package neoflex.calculator.store.entity.scoring;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import neoflex.calculator.api.dto.EmploymentDto;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ScoringEntity {
    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    MartialStatus maritalStatus;
    Integer dependentAmount;
    EmploymentEntity employment;
    String accountNumber;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
