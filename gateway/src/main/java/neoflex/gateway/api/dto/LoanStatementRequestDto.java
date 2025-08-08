package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanStatementRequestDto {

   @Schema(description = "How much client want to get",
           example = "100000")
   private BigDecimal amount;
   @Schema(description = "What is the term of the loan in months",
           example = "36")
   private Integer term;
   @Schema(description = "Client's first name",
           example = "Snake")
   private String firstName;
   @Schema(description = "Client's last name",
           example = "Solid")
   private String lastName;
   @Schema(description = "Client's middle name (if exists)",
           example = "John")
   private String middleName;
   @Schema(description = "Client's email",
           example = "solid.snake@foxhound.com")
   private String email;
   @Schema(description = "Client's birthdate",
           example = "1972-01-01")
   private LocalDate birthdate;
   @Schema(description = "Client's passport series (first four digits)",
           example = "1234")
   private String passportSeries;
   @Schema(description = "Client's passport number (last six digits)",
           example = "567890")
   private String passportNumber;
}
