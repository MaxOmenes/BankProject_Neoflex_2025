package neoflex.deal.service.local.mapper.admin;

import neoflex.deal.api.dto.ClientAdminDto;
import neoflex.deal.api.dto.StatementAdminDto;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.StatementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatementAdminMapper {
    StatementAdminDto toDto(StatementEntity statementEntity);
}
