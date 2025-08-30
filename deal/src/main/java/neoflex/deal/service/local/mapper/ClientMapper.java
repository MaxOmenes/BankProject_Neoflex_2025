package neoflex.deal.service.local.mapper;

import neoflex.deal.api.dto.FinishRegistrationRequestDto;
import neoflex.deal.api.dto.LoanStatementRequestDto;
import neoflex.deal.api.dto.PassportDto;
import neoflex.deal.store.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientEntity toClientEntity(LoanStatementRequestDto loanStatementRequestDto, PassportDto passport);
    void updateClientFromRequest(
            FinishRegistrationRequestDto request,
            @MappingTarget ClientEntity client);
}
