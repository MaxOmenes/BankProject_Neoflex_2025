package neoflex.calculator.service.prescoring;

import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.LoanOfferDto;
import neoflex.calculator.api.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class SimplePreScoringService implements PreScoringService {
    @Value("${constants.rate}")
    private Double rate;
    @Value("${constants.insuranceRate}")
    private Double insuranceRate;
    @Value("${constants.salaryClientRate}")
    private Double salaryClientRate;

    @Override
    public List<LoanOfferDto> preScore(LoanStatementRequestDto statementRequest) {
        List<LoanOfferDto> offers = new ArrayList<>();
        for (boolean isInsuranceEnabled : List.of(true, false)){
            for (boolean isSalaryClient : List.of(true, false)){
                BigDecimal currentRate = BigDecimal.valueOf(rate);

                if (isInsuranceEnabled) {
                    currentRate = currentRate.subtract(BigDecimal.valueOf(insuranceRate));
                }

                if (isSalaryClient) {
                    currentRate = currentRate.subtract(BigDecimal.valueOf(salaryClientRate));
                }

                LoanOfferDto offer = LoanOfferDto.builder()
                        .statementId(null)
                        .requestedAmount(statementRequest.getAmount())
                        .term(statementRequest.getTerm())
                        .rate(currentRate)
                        .isInsuranceEnabled(isInsuranceEnabled)
                        .isSalaryClient(isSalaryClient)
                        .build();

                offers.add(offer);
            }
        }

        return offers;
    }
}
