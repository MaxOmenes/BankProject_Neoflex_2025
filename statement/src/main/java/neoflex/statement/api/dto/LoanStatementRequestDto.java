package neoflex.statement.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanStatementRequestDto {
   @NotNull
   @Min(value = 20000, message = "Amount must be at least 20,000")
   @Schema(description = "How much client want to get",
            example = "100000")
   private BigDecimal amount;

   @NotNull
   @Min(value = 6, message = "Term must be at least 6 months")
   @Schema(description = "What is the term of the loan in months",
            example = "36")
   private Integer term;

   @NotNull
   @Size(min = 2, max = 30, message = "First name must be between 2 and 30) characters")
   @Pattern(                    // только латинские буквы A-Z / a-z
           regexp = "^[A-Za-z]+$",
           message = "Only Latin letters are allowed in the first name"
   )
   @Schema(description = "Client's first name",
            example = "Snake")
   private String firstName;

   @NotNull
   @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
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

   @NotNull
   @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$",
            message = "Email must be a valid email address")
   @Schema(description = "Client's email",
            example = "venom.snake@diamonddogs.com")
   private String email;

   @NotNull
   //TODO: (!) ADD ANNOTATION OR SERVICE TO VALIDATE DATE FORMAT
   @Schema(description = "Client's birthdate",
            example = "1972-01-01")
   private LocalDate birthdate;

   @NotNull
   @Size(min = 4, max = 4, message = "Passport series must be exactly 4 digits")
   @Schema(description = "Client's passport series (first four digits)",
            example = "1234")
   private String passportSeries;

   @NotNull
   @Size(min = 6, max = 6, message = "Passport number must be exactly 6 digits")
   @Schema(description = "Client's passport number (last six digits)",
            example = "567890")
   private String passportNumber;

   @AssertTrue(message = "Client must be at least 18 years old")
   @Schema(hidden = true)
   public boolean isAdult() {
       return birthdate != null && LocalDate.now().minusYears(18).isAfter(birthdate);
   }
}
