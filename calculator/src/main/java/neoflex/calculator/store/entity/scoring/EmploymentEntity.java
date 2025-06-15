package neoflex.calculator.store.entity.scoring;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentEntity {
    EmploymentStatus employmentStatus;
    String employerINN;
    BigDecimal salary;
    EmploymentPosition position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
