package neoflex.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PassportDto {
    @Schema(description = "Unique identifier for the passport",
            example = "00000000-4000-0000-0000-000000000000")
    private UUID passportUuid;

    @Schema(description = "Passport series (first four digits)",
            example = "1234")
    private String series;

    @Schema(description = "Passport number (last six digits)",
            example = "567890")
    private String number;

    @Schema(description = "The name or code of the government authority that issued the passport",
            example = "U.S. Department of State")
    private String issuedBranch;

    @Schema(description = "The date when the passport was issued",
            example = "2010-01-01")
    private LocalDate issuedDate;
}
