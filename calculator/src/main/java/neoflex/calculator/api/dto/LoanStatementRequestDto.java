package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanStatementRequestDto {
   @Schema(description = "How much client want to get")
   BigDecimal amount;
   @Schema(description = "What is the term of the loan in months")
   Integer term;
   @Schema(description = "Client's first name")
   String firstName;
   @Schema(description = "Client's last name")
   String lastName;
   @Schema(description = "Client's middle name (if exists)")
   String middleName;
   @Schema(description = "Client's email")
   String email;
   @Schema(description = "Client's birthdate")
   LocalDate birthdate;
   @Schema(description = "Client's passport series (first four digits)")
   String passportSeries;
   @Schema(description = "Client's passport number (last six digits)")
   String passportNumber;
}
