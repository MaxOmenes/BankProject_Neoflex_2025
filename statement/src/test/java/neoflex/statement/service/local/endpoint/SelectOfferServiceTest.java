package neoflex.statement.service.local.endpoint;

import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.service.remote.deal.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectOfferServiceTest {

    @Mock
    DealService dealService;

    @InjectMocks
    SelectOfferService selectOfferService;

    @Test
    void selectOffer() {
        LoanOfferDto offer = LoanOfferDto.builder().build();
        doNothing().when(dealService).selectOffer(offer);

        selectOfferService.selectOffer(offer);

        assertDoesNotThrow(() -> {
            selectOfferService.selectOffer(offer);
        }, "Selecting offer should not throw an exception");

    }
}