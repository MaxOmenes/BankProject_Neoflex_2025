package neoflex.deal.service.local.mapper.admin;

import neoflex.deal.api.dto.ClientAdminDto;
import neoflex.deal.store.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientAdminMapper {
    ClientAdminDto toDto(ClientEntity clientEntity);
}
