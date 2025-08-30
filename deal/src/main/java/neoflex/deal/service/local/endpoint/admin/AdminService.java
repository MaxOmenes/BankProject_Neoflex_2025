package neoflex.deal.service.local.endpoint.admin;

import lombok.RequiredArgsConstructor;
import neoflex.deal.api.dto.StatementAdminDto;
import neoflex.deal.service.local.mapper.admin.ClientAdminMapper;
import neoflex.deal.service.local.mapper.admin.CreditAdminMapper;
import neoflex.deal.service.local.mapper.admin.StatementAdminMapper;
import neoflex.deal.store.entity.ClientEntity;
import neoflex.deal.store.entity.CreditEntity;
import neoflex.deal.store.entity.StatementEntity;
import neoflex.deal.store.repository.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StatementRepository repository;
    private final ClientAdminMapper clientAdminMapper;
    private final StatementAdminMapper statementAdminMapper;
    private final CreditAdminMapper creditAdminMapper;

    public StatementAdminDto getStatement(UUID statementId) {
        StatementEntity statement = repository.findById(statementId).orElseThrow(
                () -> new IllegalArgumentException("Statement not found for ID: " + statementId)
        );

        return mapToDto(statement);
    }

    public List<StatementAdminDto> getAllStatements() {
        List<StatementEntity> statements = repository.findAll();
        return statements.stream().map(this::mapToDto).toList();
    }

    private StatementAdminDto mapToDto(StatementEntity statement) {
        StatementAdminDto dto = statementAdminMapper.toDto(statement);
        ClientEntity client = statement.getClient();
        CreditEntity credit = statement.getCredit();

        if (client != null) {
            dto.setClient(clientAdminMapper.toDto(client));
        }

        if (credit != null) {
            dto.setCredit(creditAdminMapper.toDto(credit));
        }

        return dto;
    }
}
