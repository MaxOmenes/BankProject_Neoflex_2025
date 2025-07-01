package neoflex.deal.service.local.endpoint;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.*;
import neoflex.deal.service.local.fabric.ClientEntityFabric;
import neoflex.deal.service.local.fabric.CreditEntityFabric;
import neoflex.deal.service.local.fabric.ScoringDataFabric;
import neoflex.deal.service.local.fabric.StatementEntityFabric;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.CreditEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.CreditRepository;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculateCreditService {
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;
    private final CalculatorService calculatorService;

    private final StatementEntityFabric statementEntityFabric;
    private final ScoringDataFabric scoringDataFabric;
    private final CreditEntityFabric creditEntityFabric;
    private final ClientEntityFabric clientEntityFabric;

    @Transactional
    public void calculateCredit(String statementId, FinishRegistrationRequestDto registrationRequest) {
        StatementEntity statement = statementRepository.findById(UUID.fromString(statementId)).orElseThrow(
                () -> new IllegalArgumentException("Statement not found for ID: " + statementId)
        );

        ClientEntity client = statement.getClient();
        if (client == null) {
            throw new IllegalStateException("No client found for statement ID: " + statementId);
        }
        clientEntityFabric.enrich(client, registrationRequest);

        ScoringDataDto scoringData = scoringDataFabric.create(statementId, registrationRequest, statement, client);
        CreditDto creditDto = calculatorService.calc(scoringData);
        if (creditDto == null) {
            throw new IllegalStateException("Credit calculation failed for statement ID: " + statementId);
        }

        CreditEntity credit = creditEntityFabric.create(creditDto);
        creditRepository.save(credit);
        log.info("Credit calculated and saved for statement ID: {}", statementId);


        statementEntityFabric.enrich(statement, credit);
        statementRepository.save(statement);
    }
}
