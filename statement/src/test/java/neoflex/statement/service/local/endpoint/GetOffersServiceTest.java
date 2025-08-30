package neoflex.statement.service.local.endpoint;

import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import neoflex.statement.service.remote.deal.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOffersServiceTest {
    @Mock
    DealService dealService;

    @InjectMocks
    GetOffersService getOffersService;

    @Test
    void getOffers() {
        LoanStatementRequestDto test = LoanStatementRequestDto.builder().build();
        List<LoanOfferDto> listOffers = List.of(LoanOfferDto.builder().build());

        when(dealService.getOffers(test)).thenReturn(listOffers);

        List<LoanOfferDto> offers = getOffersService.getOffers(LoanStatementRequestDto.builder().build());

        assertEquals(offers, listOffers);
    }
}