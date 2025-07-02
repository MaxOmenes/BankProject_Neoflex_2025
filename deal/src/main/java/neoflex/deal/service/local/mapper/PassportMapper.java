package neoflex.deal.service.local.mapper;

import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.api.dto.PassportDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mappings({
            @Mapping(target = "passportUuid", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(source = "passportSeries", target = "series"),
            @Mapping(source = "passportNumber", target = "number"),
    })
    PassportDto toPassportDto(LoanStatementRequestDto loanStatementRequestDto);
    @Mappings({
            @Mapping(target = "issuedDate", source = "passportIssueDate"),
            @Mapping(target = "issuedBranch", source = "passportIssueBranch")
    })
    void updatePassportFromRequest(
            FinishRegistrationRequestDto Request,
            @MappingTarget PassportDto passportDto
    );
}
