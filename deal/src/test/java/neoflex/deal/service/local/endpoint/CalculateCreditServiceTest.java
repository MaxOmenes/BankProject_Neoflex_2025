package neoflex.deal.service.local.endpoint;

import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.ScoringDataDto;
import neoflex.deal.service.local.fabric.ClientEntityFabric;
import neoflex.deal.service.local.fabric.ScoringDataFabric;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateCreditServiceTest {
    private final String zeroUuid = "00000000-0000-0000-0000-000000000000";
    @Mock
    StatementRepository statementRepository;
    @Mock
    CalculatorService calculatorService;
    @Mock
    ScoringDataFabric scoringDataFabric;
    @Mock
    ClientEntityFabric clientEntityFabric;

    @InjectMocks
    CalculateCreditService calculateCreditService;

    @Test
    void testCalculateCreditIfNoStatement(){
        when(statementRepository.findById(UUID.fromString(zeroUuid))).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            calculateCreditService.calculateCredit(zeroUuid,
                    FinishRegistrationRequestDto.builder().build());
        });
    }

    @Test
    void testCalculateCreditIfNoClient() {
        when(statementRepository.findById(UUID.fromString(zeroUuid))).thenReturn(
                Optional.of(StatementEntity.builder()
                        .build())
        );
        assertThrows(IllegalStateException.class, () -> {
            calculateCreditService.calculateCredit(zeroUuid,
                    FinishRegistrationRequestDto.builder().build());
        });
    }

    @Test
    void testCalculateCreditIfNoCredit() {
        ClientEntity client = ClientEntity.builder().build();
        StatementEntity statement = StatementEntity.builder()
                .client(client)
                .build();
        FinishRegistrationRequestDto request = FinishRegistrationRequestDto.builder().build();


        ScoringDataDto scoringEmpty = ScoringDataDto.builder().build();

        when(statementRepository.findById(UUID.fromString(zeroUuid))).thenReturn(
                Optional.of(StatementEntity.builder()
                        .client(client)
                        .build())
        );
        doNothing().when(clientEntityFabric).enrich(client, request);
        when(calculatorService.calc(scoringEmpty)).thenReturn(null);
        when(scoringDataFabric.create(any(), any(), any(), any())).thenReturn(scoringEmpty);

        assertThrows(IllegalStateException.class, () -> {
            calculateCreditService.calculateCredit(zeroUuid, request);
        });
    }
}