package neoflex.deal.service.local.endpoint;

import lombok.AllArgsConstructor;
import neoflex.deal.api.dto.*;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.CreditEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.credit.CreditStatus;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.enums.statement.status_history.ChangeType;
import neoflex.deal.store.repository.CreditRepository;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CalculateCreditService {
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;
    private final CalculatorService calculatorService;

    public void calculateCredit(String statementId, FinishRegistrationRequestDto registrationRequest) {
        Optional<StatementEntity> statement = statementRepository.findById(UUID.fromString(statementId));
        if (statement.isEmpty()) {
            throw new IllegalArgumentException("Statement not found for ID: " + statementId);
        }

        StatementEntity statementEntity = statement.get();

        LoanOfferDto appliedOffer = statementEntity.getAppliedOffer();
        ClientEntity client = statementEntity.getClient();

        if (appliedOffer == null) {
            throw new IllegalStateException("No applied offer found for statement ID: " + statementId);
        }
        if (client == null) {
            throw new IllegalStateException("No client found for statement ID: " + statementId);
        }

        EmploymentDto employmentDto = registrationRequest.getEmployment();
        employmentDto.setEmployment_uuid(UUID.randomUUID());

        client.setGender(registrationRequest.getGender());
        client.setMaritalStatus(registrationRequest.getMaritalStatus());
        client.setEmploymentDto(employmentDto);
        client.setAccountNumber(registrationRequest.getAccountNumber());

        PassportDto clientPassport = client.getPassport();

        if (clientPassport == null) {
            throw new IllegalStateException("No passport found for client ID: " + client.getClientId());
        } else {
            clientPassport.setIssuedDate(registrationRequest.getPassportIssueDate());
            clientPassport.setIssuedBranch(registrationRequest.getPassportIssueBranch());

            client.setPassport(clientPassport);
        }


        ScoringDataDto scoringData = ScoringDataDto.builder()
                .amount(appliedOffer.getRequestedAmount())
                .term(appliedOffer.getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .maritalStatus(client.getMaritalStatus())
                .birthdate(client.getBirthdate())
                .passportSeries(clientPassport.getSeries())
                .passportNumber(clientPassport.getNumber())
                .passportIssueDate(clientPassport.getIssuedDate())
                .passportIssueBranch(clientPassport.getIssuedBranch())
                .dependentAmount(registrationRequest.getDependentAmount())
                .employment(client.getEmploymentDto())
                .isInsuranceEnabled(appliedOffer.getIsInsuranceEnabled())
                .isSalaryClient(appliedOffer.getIsSalaryClient())
                .accountNumber(registrationRequest.getAccountNumber())
                .build();

        CreditDto creditDto = calculatorService.calc(scoringData);

        if (creditDto == null) {
            throw new IllegalStateException("Credit calculation failed for statement ID: " + statementId);
        }

        CreditEntity credit = CreditEntity.builder()
                .amount(creditDto.getAmount())
                .term(creditDto.getTerm())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .rate(creditDto.getRate())
                .psk(creditDto.getPsk())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .insuranceEnabled(creditDto.getIsInsuranceEnabled())
                .salaryClient(creditDto.getIsSalaryClient())
                .creditStatus(CreditStatus.CALCULATED)
                .build();

        creditRepository.save(credit);

        statementEntity.setCredit(credit);
        statementEntity.setStatus(ApplicationStatus.CC_APPROVED);
        statementEntity.getStatusHistory().add(
                StatementStatusHistoryDto.builder()
                        .changeType(ChangeType.MANUAL)
                        .status(ApplicationStatus.CC_APPROVED)
                        .timestamp(statementEntity.getCreationDate())
                        .build()
        );

        statementRepository.save(statementEntity);
    }
}
