package neoflex.dossier.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.dossier.messaging.dto.EmailMessageDto;
import neoflex.dossier.service.mail.EmailSenderService;
import neoflex.dossier.service.mail.enums.Templates;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendDocumentsTopicHandlerService implements TopicHandler<EmailMessageDto> {
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;

    @Override
    public void handle(EmailMessageDto message) {
        Context context = new Context();
        context.setVariable("message", message.getText());
        String emailContent = templateEngine.process(Templates.SEND_DOCUMENTS.getPath(), context);

        Map<String, String> attachments = Map.of(
                "Application Confirmation.pdf - Bank With Love.pdf", "attachments/application-confirmation.pdf"
        );

        emailSenderService.sendEmail(message.getAddress(), "Send Documents | Bank with Love", emailContent, attachments);
    }
}
