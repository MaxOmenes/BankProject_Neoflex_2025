package neoflex.deal.service.local.fabric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.ScoringDataDto;
import neoflex.deal.service.local.mapper.ScoringDataMapper;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScoringDataFabric {

    private final ScoringDataMapper scoringDataMapper;

    public ScoringDataDto create(String statementId, FinishRegistrationRequestDto registrationRequest,
                                 StatementEntity statement, ClientEntity client) {
        ScoringDataDto scoringData = scoringDataMapper.
                toDto(statementId, registrationRequest, statement, client);
        log.debug("Created ScoringDataDto: {}", scoringData);
        return scoringData;
    }
}
