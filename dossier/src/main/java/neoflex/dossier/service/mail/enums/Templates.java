package neoflex.dossier.service.mail.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Templates {
    CREATE_DOCUMENTS("email/create-documents-template"),
    CREDIT_ISSUED("email/credit-issued-template"),
    FINISH_REGISTRATION("email/finish-registration-template"),
    SEND_DOCUMENTS("email/send-documents-template"),
    SEND_SES("email/send-ses-template"),
    STATEMENT_DENIED("email/statement-denied-template");

    final String path;
}
