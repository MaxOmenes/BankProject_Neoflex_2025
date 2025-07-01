package neoflex.deal.service.local.endpoint;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.service.local.fabric.ClientEntityFabric;
import neoflex.deal.service.local.fabric.StatementEntityFabric;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.ClientRepository;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculateOffersService {
    private final CalculatorService calculatorService;
    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;

    private final ClientEntityFabric clientEntityFabric;
    private final StatementEntityFabric statementEntityFabric;

    @Transactional
    public List<LoanOfferDto> makeOffers(LoanStatementRequestDto statementRequest) {
        ClientEntity client = clientEntityFabric.create(statementRequest);
        client = clientRepository.save(client);
        log.info("Client saved with ID: {}", client.getClientId());

        StatementEntity statement = statementEntityFabric.create(client);
        statement = statementRepository.save(statement);
        log.info("Statement saved with ID: {}", statement.getStatementId());

        List<LoanOfferDto> offers = calculatorService.offers(statementRequest);
        if (offers.isEmpty()) {
            log.warn("No offers calculated for statement ID: {}", statement.getStatementId());
            return List.of();
        }
        log.info("Offers calculated: {}", offers);

        UUID statementId = statement.getStatementId();
        offers.forEach(offer -> {
            offer.setStatementId(statementId);
        });

        return offers;
    }
}
