package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ScoringDataDto {
    @Schema(description = "Total amount requested by the client")
    BigDecimal amount;
    @Schema(description = "How many months the loan will be active")
    Integer term;
    @Schema(description = "Client's first name")
    String firstName;
    @Schema(description = "Client's last name")
    String lastName;
    @Schema(description = "Client's middle name (if exists)")
    String middleName;
    @Schema(description = "Client's gender")
    String gender;
    @Schema(description = "Client's birthdate")
    LocalDate birthdate;
    @Schema(description = "Client's passport series (first four digits)")
    String passportSeries;
    @Schema(description = "Client's passport number (last six digits)")
    String passportNumber;
    @Schema(description = "The date when the client's passport was issued")
    LocalDate passportIssueDate;
    @Schema(description = "The name or code of the government authority that issued the passport")
    String passportIssueBranch;
    @Schema(description = "Is client married or not")
    String maritalStatus;
    @Schema(description = "The number of people financially dependent on the client (such as children, elderly parents, or other family members the client supports)")
    Integer dependentAmount;
    @Schema(description = "The income of the client, including salary, bonuses, and other forms of compensation")
    EmploymentDto employment;
    @Schema(description = "Account number of the client's bank account")
    String accountNumber;
    @Schema(description = "Is client enabled insurance or not")
    Boolean isInsuranceEnabled;
    @Schema(description = "Is client a salary client or not")
    Boolean isSalaryClient;
}
