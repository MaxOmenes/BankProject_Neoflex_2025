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
    @Schema(description ="Status of employment, e.g. 'Employed', 'Unemployed', 'Self-employed', 'Business-owner'",
            example = "EMPLOYED")
    private String employmentStatus;

    @Schema(description = "INN of the employer",
            example = "1234567890")
    private String employerINN;

    @Schema(description = "Monthly salary of the client",
            example = "50000")
    private BigDecimal salary;

    @Schema(description = "Position of the client at work, e.g. 'Manager', 'Middle-manager', 'Top-manager'",
            example = "MANAGER")
    private String position;

    @Schema(description = "Total work experience in months in all companies",
            example = "120")
    private Integer workExperienceTotal;

    @Schema(description = "Current work experience in months at the current company",
            example = "24")
    private Integer workExperienceCurrent;

}
