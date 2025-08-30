package neoflex.deal.service.local.mapper.admin;

import neoflex.deal.api.dto.CreditAdminDto;
import neoflex.deal.store.entity.CreditEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditAdminMapper {
    CreditAdminDto toDto(CreditEntity creditEntity);
}
