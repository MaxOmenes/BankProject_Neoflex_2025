package neoflex.gateway.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PassportDto {
    private UUID passportUuid;
    private String series;
    private String number;
    private String issuedBranch;
    private LocalDate issuedDate;
}
