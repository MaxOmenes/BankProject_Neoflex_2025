package neoflex.deal.service.local.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.api.dto.PassportDto;
import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.enums.statement.status_history.ChangeType;
import neoflex.deal.store.repository.ClientRepository;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculateOffersService {
    private final CalculatorService calculatorService;
    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;

    public List<LoanOfferDto> makeOffers(LoanStatementRequestDto statementRequest) {
        PassportDto passport = PassportDto.builder()
                .passportUuid(UUID.randomUUID())
                .series(statementRequest.getPassportSeries())
                .number(statementRequest.getPassportNumber())
                .build();

        log.info("Passport created: {}", passport);

        ClientEntity client = ClientEntity.builder()
                .firstName(statementRequest.getFirstName())
                .lastName(statementRequest.getLastName())
                .middleName(statementRequest.getMiddleName())
                .birthdate(statementRequest.getBirthdate())
                .email(statementRequest.getEmail())
                .passport(passport)
                .build();

        log.info("Client created: {}", client);

        client = clientRepository.save(client);

        log.info("Client saved with ID: {}", client.getClientId());

        StatementEntity statement = StatementEntity.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .statusHistory(List.of(StatementStatusHistoryDto.builder()
                        .changeType(ChangeType.MANUAL) //TODO: (!) when it change?
                        .timestamp(LocalDate.now())
                        .status(ApplicationStatus.PREAPPROVAL)
                        .build()))
                .build();

        log.info("Statement created: {}", statement);

        statement = statementRepository.save(statement);

        log.info("Statement saved with ID: {}", statement.getStatementId());

        List<LoanOfferDto> offers = calculatorService.offers(statementRequest);

        log.info("Offers calculated: {}", offers);

        UUID statementId = statement.getStatementId();

        offers.forEach(offer -> {
            offer.setStatementId(statementId);
        });

        return offers;
    }
}
