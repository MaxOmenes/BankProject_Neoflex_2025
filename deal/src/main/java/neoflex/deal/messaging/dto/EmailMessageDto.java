package neoflex.deal.messaging.dto;

import lombok.Builder;
import lombok.Data;
import neoflex.deal.messaging.dto.enums.Subject;

import java.util.UUID;

@Data
@Builder
public class EmailMessageDto {
    private String address;
    private Subject subject; //ASK: Theme in NeoStudy
    private UUID statementId;
    private String text;
}
