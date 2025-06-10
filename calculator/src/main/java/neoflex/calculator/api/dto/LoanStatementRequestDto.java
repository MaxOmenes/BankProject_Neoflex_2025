package neoflex.calculator.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanStatementRequestDto {
   BigDecimal amount;
   Integer term;
   String firstName;
   String lastName;
   String middleName;
   String email;
   LocalDate birthdate;
   String passportSeries;
   String passportNumber;
}
