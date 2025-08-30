package neoflex.deal.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import neoflex.deal.store.enums.client.Gender;
import neoflex.deal.store.enums.client.MaritalStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ClientAdminDto {
    @Schema(description = "Unique identifier of the client",
            example = "00000000-4000-0000-0000-000000000001")
    private UUID clientId;

    @Schema(description = "Client's last name",
            example = "Solid")
    private String lastName;

    @Schema(description = "Client's first name",
            example = "Snake")
    private String firstName;

    @Schema(description = "Client's middle name",
            example = "John")
    private String middleName;

    @Schema(description = "Client's date of birth",
            example = "1972-01-01")
    private LocalDate birthdate;

    @Schema(description = "Client's email address",
            example = "snake.solid@example.com")
    private String email;

    @Schema(description = "Client's gender",
            example = "MALE")
    private Gender gender;

    @Schema(description = "Client's marital status",
            example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Schema(description = "Number of dependents",
            example = "2")
    private Integer dependentAmount;

    @Schema(description = "Client's passport information")
    private PassportDto passport;

    @Schema(description = "Client's employment information")
    private EmploymentDto employmentDto;

    @Schema(description = "Client's bank account number",
            example = "12345678901234567890")
    private String accountNumber;
}
