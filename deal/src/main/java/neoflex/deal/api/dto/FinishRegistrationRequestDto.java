package neoflex.deal.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import neoflex.deal.store.enums.client.Gender;
import neoflex.deal.store.enums.client.MaritalStatus;

import java.time.LocalDate;

@Data
@Builder
public class FinishRegistrationRequestDto {
    @Schema(description = "Gender of the client, e.g. 'MALE', 'FEMALE'")
    private Gender gender;

    @Schema(description = "Marital status of the client, e.g. 'SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED'")
    private MaritalStatus maritalStatus;

    @Schema(description = "Number of dependents")
    private Integer dependentAmount;

    @Schema(description = "Passport issue date")
    private LocalDate passportIssueDate;

    @Schema(description = "Passport issue branch")
    private String passportIssueBranch;

    @Schema(description = "Employment information of the client")
    private EmploymentDto employment;

    @Schema(description = "Client's bank account number")
    private String accountNumber;
}
