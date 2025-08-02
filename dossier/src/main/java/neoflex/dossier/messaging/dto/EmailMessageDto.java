package neoflex.dossier.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import neoflex.dossier.messaging.dto.enums.Subject;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDto {
    private String address;
    private Subject subject; //ASK: Theme in NeoStudy
    private UUID statementId;
    private String text;
}

