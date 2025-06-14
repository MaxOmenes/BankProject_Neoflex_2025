package neoflex.calculator.store.entity.offer;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class LoanStatementRequestEntity {
    UUID id; //TODO: WHERE TO GENERATE ID?
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
