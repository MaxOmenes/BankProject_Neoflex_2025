package neoflex.deal.service.local.endpoint;

import neoflex.deal.api.dto.LoanOfferDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.service.local.fabric.ClientEntityFabric;
import neoflex.deal.service.local.fabric.StatementEntityFabric;
import neoflex.deal.service.remote.calculator.CalculatorService;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.ClientRepository;
import neoflex.deal.store.repository.StatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateOffersServiceTest {
    @Mock
    CalculatorService calculatorService;
    @Mock
    ClientRepository clientRepository;
    @Mock
    StatementRepository statementRepository;
    @Mock
    ClientEntityFabric clientEntityFabric;
    @Mock
    StatementEntityFabric statementEntityFabric;

    @InjectMocks
    CalculateOffersService calculateOffersService;

    @Test
    void testNoOffersCalculated() {
        // Given a LoanStatementRequestDto with no offers
        LoanStatementRequestDto statementRequest = LoanStatementRequestDto.builder().build();
        ClientEntity client = ClientEntity.builder().build();
        StatementEntity statement = StatementEntity.builder().build();
        // Mock the behavior of the calculatorService to return an empty list
        when(calculatorService.offers(any(LoanStatementRequestDto.class))).thenReturn(List.of());
        when(clientRepository.save(any())).thenReturn(client);
        when(statementEntityFabric.create(client)).thenReturn(statement);
        when(statementRepository.save(statement)).thenReturn(statement);

        // When makeOffers is called
        List<LoanOfferDto> offers = calculateOffersService.makeOffers(statementRequest);

        // Then the result should be an empty list
        assertTrue(offers.isEmpty(), "Expected no offers to be calculated");
    }

    @Test
    void testOffersCalculated() {
        // Given a LoanStatementRequestDto with offers
        LoanStatementRequestDto statementRequest = LoanStatementRequestDto.builder().build();
        ClientEntity client = ClientEntity.builder().build();
        StatementEntity statement = StatementEntity.builder().build();
        LoanOfferDto offer1 = LoanOfferDto.builder().build();
        LoanOfferDto offer2 = LoanOfferDto.builder().build();

        // Mock the behavior of the calculatorService to return a list of offers
        when(calculatorService.offers(any(LoanStatementRequestDto.class))).thenReturn(List.of(offer1, offer2));
        when(clientRepository.save(any())).thenReturn(client);
        when(statementEntityFabric.create(client)).thenReturn(statement);
        when(statementRepository.save(statement)).thenReturn(statement);

        // When makeOffers is called
        List<LoanOfferDto> offers = calculateOffersService.makeOffers(statementRequest);

        // Then the result should contain the offers
        assertEquals(2, offers.size(), "Expected two offers to be calculated");
    }
}