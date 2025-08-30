package neoflex.deal.service.local.fabric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.EmploymentDto;
import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.api.dto.PassportDto;
import neoflex.deal.service.local.mapper.ClientMapper;
import neoflex.deal.service.local.mapper.PassportMapper;
import neoflex.deal.store.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientEntityFabric {
    private final PassportMapper passportMapper;
    private final ClientMapper clientMapper;

    public ClientEntity create(LoanStatementRequestDto statementRequest) {
        PassportDto passport = passportMapper.toPassportDto(statementRequest);
        log.info("Passport created: {}", passport);

        ClientEntity client = clientMapper.toClientEntity(statementRequest, passport);
        log.info("Client created: {}", client);

        return client;
    }

    public void enrich(ClientEntity client, FinishRegistrationRequestDto registrationRequest) {
        EmploymentDto employmentDto = registrationRequest.getEmployment();
        employmentDto.setEmploymentUuid(UUID.randomUUID());

        clientMapper.updateClientFromRequest(registrationRequest, client);
        client.setEmploymentDto(employmentDto);

        passportMapper.updatePassportFromRequest(registrationRequest, client.getPassport());
        log.info("Client updated: {}", client);
    }
}
