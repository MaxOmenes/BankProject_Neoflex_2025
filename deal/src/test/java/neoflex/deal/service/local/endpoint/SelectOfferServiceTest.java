package neoflex.deal.service.local.endpoint;

import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.StatementStatusHistoryDto;
import neoflex.deal.service.local.endpoint.offer.SelectOfferService;
import neoflex.deal.service.local.fabric.StatementStatusHistoryFabric;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.enums.statement.ApplicationStatus;
import neoflex.deal.store.enums.statement.status_history.ChangeType;
import neoflex.deal.store.repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectOfferServiceTest {
    private final String zeroUuid = "00000000-0000-0000-0000-000000000000";

    @Mock
    StatementRepository statementRepository;
    @Mock
    StatementStatusHistoryFabric statementStatusHistoryFabric;

    @InjectMocks
    SelectOfferService selectOfferService;

    @Test
    void testCheckWithoutStatement() {
        LoanOfferDto offer = LoanOfferDto.builder().build();
        offer.setStatementId(UUID.fromString(zeroUuid));
        when(statementRepository.findById(UUID.fromString(zeroUuid)))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            selectOfferService.selectOffer(offer);
        });
    }
    @Test
    void testSelectOffer() {
        LoanOfferDto offer = LoanOfferDto.builder().build();
        StatementEntity statement = StatementEntity.builder()
                .statusHistory(new ArrayList<StatementStatusHistoryDto>()).build();
        StatementStatusHistoryDto historyDto = StatementStatusHistoryDto.builder()
                .changeType(ChangeType.MANUAL) //TODO: (!) when it change?
                .timestamp(LocalDate.now())
                .status(ApplicationStatus.APPROVED)
                .build();
        offer.setStatementId(UUID.fromString(zeroUuid));

        when(statementRepository.findById(UUID.fromString(zeroUuid)))
                .thenReturn(Optional.of(statement));
        when(statementRepository.save(statement)).thenReturn(statement);
        when(statementStatusHistoryFabric.create(ApplicationStatus.APPROVED)).thenReturn(historyDto);

        ArgumentCaptor<StatementEntity> captor = ArgumentCaptor.forClass(StatementEntity.class);


        selectOfferService.selectOffer(offer);

        Mockito.verify(statementRepository, Mockito.times(1))
                .save(captor.capture());

        StatementEntity capture = captor.getValue();
        assertNotNull(capture);
        assertEquals(offer, capture.getAppliedOffer());
        assertEquals(ApplicationStatus.APPROVED, capture.getStatus());
        assertEquals(ApplicationStatus.APPROVED, statement.getStatusHistory().getLast().getStatus());

    }
}