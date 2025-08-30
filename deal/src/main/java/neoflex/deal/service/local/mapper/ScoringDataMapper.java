package neoflex.deal.service.local.mapper;

import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.ScoringDataDto;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {
    @Mappings({
            @Mapping(target = "amount", source = "statement.appliedOffer.requestedAmount"),
            @Mapping(target = "isInsuranceEnabled", source = "statement.appliedOffer.isInsuranceEnabled"),
            @Mapping(target = "isSalaryClient", source = "statement.appliedOffer.isSalaryClient"),
            @Mapping(target = "term", source = "statement.appliedOffer.term"),
            @Mapping(target = "passportSeries", source = "client.passport.series"),
            @Mapping(target = "passportNumber", source = "client.passport.number"),
            @Mapping(target = "gender", source = "registrationRequest.gender"),
            @Mapping(target = "maritalStatus", source = "registrationRequest.maritalStatus"),
            @Mapping(target = "dependentAmount", source = "registrationRequest.dependentAmount"),
            @Mapping(target = "accountNumber", source = "registrationRequest.accountNumber"),
    })
    ScoringDataDto toDto(String statementId, FinishRegistrationRequestDto registrationRequest,
                         StatementEntity statement, ClientEntity client);
}
