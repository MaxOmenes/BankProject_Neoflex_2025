package neoflex.deal.service.local.business.ses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.store.entity.StatementEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SesCodeService {
    public void createCode(StatementEntity statement) {
        log.info("Creating SES code for statement: {}", statement.getStatementId());

        String uuid = UUID.randomUUID().toString();
        String sesCode = uuid.substring(uuid.lastIndexOf("-") + 1);
        log.info("Generated SES code: {}", sesCode);

        statement.setSesCode(sesCode);
    }
}
