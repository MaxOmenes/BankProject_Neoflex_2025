package neoflex.calculator.api.factory;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;
import neoflex.calculator.store.entity.scoring.ScoringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoringDataDtoFactory implements DtoToEntityFactory<ScoringDataDto, ScoringEntity>{

    private final EmploymentDtoFactory employmentDtoFactory;

    @Override
    public ScoringEntity toEntity(ScoringDataDto dto) {
        return ScoringEntity.builder()
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .gender(
                        Gender.valueOf(dto.getGender())
                )
                .birthdate(dto.getBirthdate())
                .passportSeries(dto.getPassportSeries())
                .passportNumber(dto.getPassportNumber())
                .passportIssueDate(dto.getPassportIssueDate())
                .passportIssueBranch(dto.getPassportIssueBranch())
                .maritalStatus(
                        MartialStatus.valueOf(dto.getMaritalStatus())
                )
                .dependentAmount(dto.getDependentAmount())
                .employment(
                        employmentDtoFactory.toEntity(dto.getEmployment())
                )
                .accountNumber(dto.getAccountNumber())
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();
    }
}
