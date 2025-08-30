package neoflex.statement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neoflex.statement.api.dto.LoanOfferDto;
import neoflex.statement.api.dto.LoanStatementRequestDto;
import neoflex.statement.service.local.endpoint.GetOffersService;
import neoflex.statement.service.local.endpoint.SelectOfferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statement")
public class StatementController {
    private final GetOffersService getOffersService;
    private final SelectOfferService selectOfferService;

    @Operation(
            summary = "Get Loan Offers",
            description = "Retrieves potential loan offers based on the provided loan statement request.")
    @PostMapping
    public List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto request){
        return getOffersService.getOffers(request);
    }

    @Operation(
            summary = "Select Loan Offer",
            description = "Selects a specific loan offer from the available options."
    )
    @PostMapping("/offer")
    public void selectOffer (@RequestBody LoanOfferDto offer) {
        selectOfferService.selectOffer(offer);
    }
}
