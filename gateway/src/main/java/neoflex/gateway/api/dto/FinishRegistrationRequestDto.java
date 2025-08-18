package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import neoflex.gateway.store.enums.client.Gender;
import neoflex.gateway.store.enums.client.MaritalStatus;

import java.time.LocalDate;

@Data
@Builder
public class FinishRegistrationRequestDto {
    @Schema(description = "Gender of the client, e.g. 'MALE', 'FEMALE'",
            example = "MALE")
    private Gender gender;

    @Schema(description = "Marital status of the client, e.g. 'SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED'",
            example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Schema(description = "Number of dependents",
            example = "2")
    private Integer dependentAmount;

    @Schema(description = "Passport issue date",
            example = "2010-01-01")
    private LocalDate passportIssueDate;

    @Schema(description = "Passport issue branch",
            example = "U.S. Department of State")
    private String passportIssueBranch;

    @Schema(description = "Employment information of the client")
    private EmploymentDto employment;

    @Schema(description = "Client's bank account number",
            example = "12345678901234567890")
    private String accountNumber;
}
