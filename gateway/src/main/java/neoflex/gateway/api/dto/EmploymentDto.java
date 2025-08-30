package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import neoflex.gateway.store.enums.employment.EmploymentPosition;
import neoflex.gateway.store.enums.employment.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class EmploymentDto {
    @Nullable
    @Schema(description = "Unique identifier of the employment record",
            example = "00000000-4000-0000-0000-000000000000")
    private UUID employmentUuid;

    @Nullable
    @Schema(description ="Status of employment, e.g. 'Employed', 'Unemployed', 'Self-employed', 'Business-owner'",
            example = "EMPLOYED")
    private EmploymentStatus employmentStatus;

    @Schema(description = "INN of the employer",
            example = "1234567890")
    private String employerINN;

    @Schema(description = "Monthly salary of the client",
            example = "50000")
    private BigDecimal salary;

    @Nullable
    @Schema(description = "Position of the client at work, e.g. 'Manager', 'Middle-manager', 'Top-manager'",
            example = "TOP_MANAGER")
    private EmploymentPosition position;

    @Schema(description = "Total work experience in months in all companies",
            example = "120")
    private Integer workExperienceTotal;

    @Schema(description = "Current work experience in months at the current company",
            example = "24")
    private Integer workExperienceCurrent;

}
