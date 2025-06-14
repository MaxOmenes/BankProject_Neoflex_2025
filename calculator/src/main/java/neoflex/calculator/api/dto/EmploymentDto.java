package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    @Schema(description ="Status of employment, e.g. 'Employed', 'Unemployed', 'Self-employed', 'Business-owner'")
    String employmentStatus;
    @Schema(description = "INN of the employer")
    String employerINN;
    @Schema(description = "Monthly salary of the client")
    BigDecimal salary;
    @Schema(description = "Position of the client at work, e.g. 'Manager', 'Middle-manager', 'Top-manager'")
    String position; //TODO: Ask what is it means and what enum i should make
    @Schema(description = "Total work experience in months in all companies")
    Integer workExperienceTotal;
    @Schema(description = "Current work experience in months at the current company")
    Integer workExperienceCurrent;

}
