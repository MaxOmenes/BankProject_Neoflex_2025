package neoflex.calculator.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDto {
    String employmentStatus;
    String employerINN;
    BigDecimal salary;
    String position; //TODO: Ask what is it means and what enum i should make
    Integer workExperienceTotal;
    Integer workExperienceCurrent;

}
