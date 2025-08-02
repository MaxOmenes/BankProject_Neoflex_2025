package neoflex.dossier.messaging.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//ASK: same as topics
@Getter
@RequiredArgsConstructor
public enum Subject {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    CREDIT_ISSUED("credit-issued"),
    STATEMENT_DENIED("statement-denied");

    private final String name;
}
