package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoanStatementRequestDtoFactory implements
        DtoToEntityFactory<LoanStatementRequestDto, LoanStatementRequestEntity> {
    @Override
    public LoanStatementRequestEntity toEntity(LoanStatementRequestDto dto) {
        return LoanStatementRequestEntity.builder()
                .id(UUID.randomUUID()) //TODO: WHERE I SHOULD GENERATE ID?
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .passportSeries(dto.getPassportSeries())
                .passportNumber(dto.getPassportNumber())
                .build();
    }
}
