package neoflex.deal.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class PassportDto {
    private UUID passport_uuid;
    private String series;
    private String number;
    private String issued_branch;
    private String issued_date;
}
