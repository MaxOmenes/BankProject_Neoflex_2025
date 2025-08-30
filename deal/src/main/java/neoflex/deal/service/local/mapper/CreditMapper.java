package neoflex.deal.service.local.mapper;

import neoflex.deal.api.dto.CreditDto;
import neoflex.deal.store.entity.CreditEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "salaryClient", source = "isSalaryClient")
    @Mapping(target = "insuranceEnabled", source = "isInsuranceEnabled")
    @Mapping(target = "creditStatus", expression = "java(neoflex.deal.store.enums.credit.CreditStatus.CALCULATED)")
    CreditEntity toEntity(CreditDto creditDto);
}
