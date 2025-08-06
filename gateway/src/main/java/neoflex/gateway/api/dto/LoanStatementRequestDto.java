package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanStatementRequestDto {
   @Schema(description = "How much client want to get")
   private BigDecimal amount;
   @Schema(description = "What is the term of the loan in months")
   private Integer term;
   @Schema(description = "Client's first name")
   private String firstName;
   @Schema(description = "Client's last name")
   private String lastName;
   @Schema(description = "Client's middle name (if exists)")
   private String middleName;
   @Schema(description = "Client's email")
   private String email;
   @Schema(description = "Client's birthdate")
   private LocalDate birthdate;
   @Schema(description = "Client's passport series (first four digits)")
   private String passportSeries;
   @Schema(description = "Client's passport number (last six digits)")
   private String passportNumber;
}
