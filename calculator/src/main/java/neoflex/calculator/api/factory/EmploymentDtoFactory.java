package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.EmploymentDto;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;
import neoflex.calculator.store.entity.scoring.EmploymentEntity;
import org.springframework.stereotype.Component;

@Component
public class EmploymentDtoFactory implements DtoToEntityFactory<EmploymentDto, EmploymentEntity>{
    @Override
    public EmploymentEntity toEntity(EmploymentDto dto) {
        return EmploymentEntity.builder()
                .employmentStatus(EmploymentStatus.valueOf(dto.getEmploymentStatus()))
                .employerINN(dto.getEmployerINN())
                .salary(dto.getSalary())
                .position(EmploymentPosition.valueOf(dto.getPosition()))
                .workExperienceTotal(dto.getWorkExperienceTotal())
                .workExperienceCurrent(dto.getWorkExperienceCurrent())
                .build();
    }
}
