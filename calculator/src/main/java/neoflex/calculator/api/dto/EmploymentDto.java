package neoflex.calculator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
public class EmploymentDto {
    @Schema(description ="Status of employment, e.g. 'Employed', 'Unemployed', 'Self-employed', 'Business-owner'")
    private String employmentStatus;

    @Schema(description = "INN of the employer")
    private String employerINN;

    @Schema(description = "Monthly salary of the client")
    private BigDecimal salary;

    @Schema(description = "Position of the client at work, e.g. 'Manager', 'Middle-manager', 'Top-manager'")
    private String position;

    @Schema(description = "Total work experience in months in all companies")
    private Integer workExperienceTotal;

    @Schema(description = "Current work experience in months at the current company")
    private Integer workExperienceCurrent;

}
