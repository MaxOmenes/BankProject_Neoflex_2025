package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import neoflex.deal.store.enums.employment.EmploymentPosition;
import neoflex.deal.store.enums.employment.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class EmploymentDto {
    @Nullable
    @Schema(description = "Unique identifier of the employment record")
    private UUID employmentUuid;

    @Nullable
    @Schema(description ="Status of employment, e.g. 'Employed', 'Unemployed', 'Self-employed', 'Business-owner'")
    private EmploymentStatus employmentStatus;

    @Schema(description = "INN of the employer")
    private String employerINN;

    @Schema(description = "Monthly salary of the client")
    private BigDecimal salary;

    @Nullable
    @Schema(description = "Position of the client at work, e.g. 'Manager', 'Middle-manager', 'Top-manager'")
    private EmploymentPosition position;

    @Schema(description = "Total work experience in months in all companies")
    private Integer workExperienceTotal;

    @Schema(description = "Current work experience in months at the current company")
    private Integer workExperienceCurrent;

}
