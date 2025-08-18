package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import neoflex.gateway.store.enums.client.Gender;
import neoflex.gateway.store.enums.client.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ScoringDataDto {
    @NotNull
    @Min(value = 20000, message = "Amount must be at least 20,000")
    @Schema(description = "Total amount requested by the client",
            example = "200000")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Term must be at least 6 months")
    @Schema(description = "How many months the loan will be active",
            example = "12")
    private Integer term;

    @NotNull
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(                    // только латинские буквы A-Z / a-z
            regexp = "^[A-Za-z]+$",
            message = "Only Latin letters are allowed in the first name"
    )
    @Schema(description = "Client's first name",
            example = "Snake")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(                    // только латинские буквы A-Z / a-z
            regexp = "^[A-Za-z]+$",
            message = "Only Latin letters are allowed in the last name"
    )
    @Schema(description = "Client's last name",
            example = "Solid")
    private String lastName;

    @Size(min = 2, max = 30, message = "Middle name must be between 2 and 30 characters")
    @Pattern(
            regexp = "^(?:[A-Za-z]{2,30})?$",
            message = "Only Latin letters are allowed in the middle name (if exists)"
    )
    @Schema(description = "Client's middle name (if exists)",
            example = "John")
    private String middleName;

    @Schema(description = "Client's gender",
            example = "MALE")
    private Gender gender;

    @Schema(description = "Client's birthdate",
            example = "1972-01-01")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "Passport series must be exactly 4 digits")
    @Schema(description = "Client's passport series (first four digits)",
            example = "1234")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "Passport number must be exactly 6 digits")
    @Schema(description = "Client's passport number (last six digits)",
            example = "567890")
    private String passportNumber;

    @Schema(description = "The date when the client's passport was issued",
            example = "2010-01-01")
    private LocalDate passportIssueDate;

    @Schema(description = "The name or code of the government authority that issued the passport",
            example = "U.S. Department of State")
    private String passportIssueBranch;

    @Schema(description = "Is client married or not",
            example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Schema(description = "The number of people financially dependent on the client (such as children, elderly parents, or other family members the client supports)",
            example = "2")
    private Integer dependentAmount;

    @NotNull
    @Schema(description = "The income of the client, including salary, bonuses, and other forms of compensation")
    private EmploymentDto employment;

    @NotNull
    @Schema(description = "Account number of the client's bank account",
            example = "12345678901234567890")
    private String accountNumber;

    @NotNull
    @Schema(description = "Is client enabled insurance or not",
            example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull
    @Schema(description = "Is client a salary client or not",
            example = "true")
    private Boolean isSalaryClient;
}
