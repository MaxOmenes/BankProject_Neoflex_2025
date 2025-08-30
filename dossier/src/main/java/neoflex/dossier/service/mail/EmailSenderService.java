package neoflex.dossier.service.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String body){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);

            log.info("Sending email to: {}, subject: {}", to, subject);

            mailSender.send(message);

            log.info("Email sent successfully to: {}", to);
        }catch (Exception e){
            log.error("Failed to send email to: {}, subject: {}, error: {}", to, subject, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendEmail(String to, String subject, String body, Map<String, String> attachments){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);

            for (Map.Entry<String, String> entry : attachments.entrySet()) {
                String fileName = entry.getKey();
                String filePath = entry.getValue();

                ClassPathResource resource = new ClassPathResource(filePath);

                // Копируем ресурс во временный файл
                File tempFile = File.createTempFile("attachment-", "-" + fileName);
                tempFile.deleteOnExit(); // Удалится после завершения работы JVM
                try (InputStream in = resource.getInputStream()) {
                    Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                messageHelper.addAttachment(fileName, tempFile);
            }

            log.info("Sending email to: {}, subject: {}", to, subject);

            mailSender.send(message);

            log.info("Email sent successfully to: {}", to);
        }catch (Exception e){
            log.error("Failed to send email to: {}, subject: {}, error: {}", to, subject, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
